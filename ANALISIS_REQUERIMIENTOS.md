# ANÁLISIS DE REQUERIMIENTOS - PROYECTO FITNESS UNIVERSITARIO

**Fecha de análisis:** 2025-11-09
**Estado:** Base de datos implementada, lógica de negocio parcial

---

## RESUMEN EJECUTIVO

### Estado General del Proyecto
- ✅ **Base de Datos:** 100% implementada (SQL + MongoDB)
- ⚠️ **Lógica de Negocio:** 70% implementada
- ❌ **Seguridad/Permisos:** 0% implementada
- ⚠️ **Interfaz de Usuario:** 60% implementada
- ❌ **Estadísticas en SQL:** 0% implementada
- ⚠️ **Reportes Innovadores:** 10% implementada

---

## ANÁLISIS DETALLADO POR REQUERIMIENTO

### 1. AUTENTICACIÓN Y USUARIOS

#### Requerimiento del PDF:
> "Un usuario debe poder iniciar sesión con su cuenta institucional. Tanto los estudiantes, como los colaboradores y entrenadores tienen cuenta institucional y su información se encuentra en una base de datos PostgreSQL."

#### ✅ IMPLEMENTADO:
- Login básico con usuario/contraseña (`AuthMvcController`)
- Integración con BD PostgreSQL/H2 (tabla `USERS`)
- Sincronización automática SQL → MongoDB al hacer login
- Redirección basada en roles (ADMIN, TRAINER, STUDENT, EMPLOYEE)
- Extracción de datos desde Student/Employee

#### ❌ FALTANTE:
1. **CRÍTICO - Sistema de Seguridad:**
   - No hay Spring Security implementado
   - No hay gestión de sesiones
   - Contraseñas en texto plano (no hasheadas)
   - Cualquier usuario puede acceder a cualquier URL sin validación

2. **CRÍTICO - Control de Acceso por Roles:**
   - No hay `@PreAuthorize` ni `@Secured`
   - No hay filtros de seguridad
   - Navbar muestra todas las opciones a todos los usuarios

3. **Funcionalidad básica:**
   - No hay logout implementado
   - No hay "recordar sesión"
   - No hay recuperación de contraseña

**ARCHIVOS AFECTADOS:**
- `src/main/java/org/example/polyglotdataproyect/controller/mvc/AuthMvcController.java`
- `src/main/java/org/example/polyglotdataproyect/services/AuthenticationService.java`
- Falta: `SecurityConfig.java`, `UserDetailsService.java`

**PRIORIDAD:** 🔴 CRÍTICA

---

### 2. GESTIÓN DE EJERCICIOS

#### Requerimiento del PDF:
> "El sistema debe permitir al usuario ingresar y registrar sus rutinas de ejercicio. Se deben poder elegir de ejercicios predefinidos o agregar personalizados. De los ejercicios se tiene nombre, tipo (cardio, fuerza, movilidad), descripción, duración, dificultad y videos demostrativos."

#### ✅ IMPLEMENTADO:
- Entidad `Exercise` en MongoDB con todos los campos requeridos:
  - `name` (String)
  - `type` (String): cardio, fuerza, movilidad
  - `description` (String)
  - `durationMin` (Integer)
  - `difficulty` (String): beginner, intermediate, advanced
  - `videoUrl` (String)
  - `createdBy` (String): tracking del creador
- `ExerciseService` con CRUD completo
- `ExerciseRepository` con filtros por tipo, dificultad, creador
- `ExerciseMvcController` con endpoints para crear, listar, editar, eliminar
- Template `exercises/list.html` para biblioteca de ejercicios
- Template `exercises/create.html` para crear ejercicios
- Filtros de búsqueda (por tipo, dificultad, nombre)

#### ⚠️ PARCIALMENTE IMPLEMENTADO:
- Los usuarios pueden crear ejercicios personalizados
- Los ejercicios predefinidos se pueden crear pero no hay seed data (solo 1 ejercicio de ejemplo)

#### ❌ FALTANTE:
1. **Control de permisos:**
   - Cualquier usuario puede crear/editar/eliminar ejercicios
   - Debería: Solo TRAINER puede crear ejercicios predefinidos
   - Debería: Los usuarios pueden crear ejercicios personalizados solo para ellos

2. **Templates faltantes:**
   - `exercises/view.html` - Ver detalle de ejercicio
   - `exercises/edit.html` - Editar ejercicio

3. **Funcionalidad:**
   - No hay distinción entre ejercicios públicos y privados
   - No hay categorización de ejercicios predefinidos vs personalizados
   - Falta seed data con ejercicios iniciales

**ARCHIVOS AFECTADOS:**
- `src/main/java/org/example/polyglotdataproyect/entities/Exercise.java` ✅
- `src/main/java/org/example/polyglotdataproyect/services/ExerciseService.java` ✅
- `src/main/java/org/example/polyglotdataproyect/controller/mvc/ExerciseMvcController.java` ⚠️
- `src/main/resources/templates/exercises/view.html` ❌
- `src/main/resources/templates/exercises/edit.html` ❌

**PRIORIDAD:** 🟡 MEDIA (funcional pero sin permisos)

---

### 3. GESTIÓN DE RUTINAS

#### Requerimiento del PDF:
> "El sistema debe permitir al usuario ingresar y registrar sus rutinas de ejercicio."

#### ✅ IMPLEMENTADO:
- Entidad `Routine` en MongoDB:
  - `name` (String)
  - `ownerId` (String): dueño de la rutina
  - `isTemplate` (Boolean): indica si es plantilla
  - `copiedFrom` (String): ID de rutina original si fue copiada
  - `exercises` (List<ExerciseInRoutine>): ejercicios con sets, reps, duración
  - `createdAt` (Date)
- `RoutineService` con CRUD completo
- Funcionalidad de copiar rutinas (`copyRoutineForUser`)
- Composición de ejercicios en rutinas (sets, reps, duration)
- `RoutineMvcController` con endpoints completos
- Template `routines/create.html` para crear rutinas
- Template `routines/view.html` para ver rutinas
- Template `routines/predefined.html` para plantillas

#### ⚠️ PARCIALMENTE IMPLEMENTADO:
- Sistema de plantillas existe pero falta UI completa
- Copiar rutinas funciona pero no hay validación de permisos

#### ❌ FALTANTE:
1. **Control de permisos:**
   - Cualquier usuario puede editar/eliminar rutinas de otros
   - No hay validación de ownership

2. **Templates faltantes:**
   - `routines/list.html` - Lista de rutinas del usuario
   - `routines/edit.html` - Editar rutina
   - `routines/templates.html` - Lista de plantillas disponibles

3. **Funcionalidad:**
   - No se puede ajustar una rutina copiada (el requerimiento dice que sí)
   - Falta seed data con rutinas prediseñadas iniciales

**ARCHIVOS AFECTADOS:**
- `src/main/java/org/example/polyglotdataproyect/entities/Routine.java` ✅
- `src/main/java/org/example/polyglotdataproyect/services/RoutineService.java` ✅
- `src/main/java/org/example/polyglotdataproyect/controller/mvc/RoutineMvcController.java` ⚠️
- `src/main/resources/templates/routines/list.html` ❌
- `src/main/resources/templates/routines/edit.html` ❌
- `src/main/resources/templates/routines/templates.html` ❌

**PRIORIDAD:** 🟡 MEDIA (funcional pero sin permisos y templates)

---

### 4. REGISTRO DE PROGRESO

#### Requerimiento del PDF:
> "Los usuarios, deben poder registrar el progreso diario o semanal (por ejemplo: repeticiones, tiempo, nivel de esfuerzo)."

#### ✅ IMPLEMENTADO:
- Entidad `ProgressEntry` en MongoDB:
  - `userId` (String)
  - `routineId` (String)
  - `entries` (List<Entry>): array de registros
    - `date` (Date)
    - `completedExercises` (Integer)
    - `effortLevel` (Integer): 1-10
    - `trainerFeedback` (String): opcional
- `ProgressService` con métodos para:
  - Agregar entries de progreso
  - Obtener progreso por usuario
  - Obtener progreso por rutina
  - Agregar feedback del entrenador
- `ProgressMvcController` con endpoints
- Template `progress/log.html` para registrar progreso
- Template `progress/view.html` para ver progreso

#### ⚠️ PARCIALMENTE IMPLEMENTADO:
- El modelo permite repeticiones/tiempo/esfuerzo pero solo se registra `completedExercises` y `effortLevel`
- No hay detalle por ejercicio individual dentro de la rutina

#### ❌ FALTANTE:
1. **Detalle de progreso:**
   - No se registra progreso por ejercicio individual (solo total)
   - Falta: repeticiones, series, peso, tiempo por ejercicio

2. **Templates faltantes:**
   - `progress/detail.html` - Ver detalle de progreso específico

3. **Control de permisos:**
   - No hay validación de que el usuario solo registre su propio progreso

**MODELO SUGERIDO MEJORADO:**
```javascript
{
  userId: "2001",
  routineId: "abc123",
  entries: [
    {
      date: ISODate("2025-11-09"),
      exercises: [
        {
          exerciseId: "ex1",
          sets: 3,
          reps: 12,
          weight: 50,
          durationMin: 15,
          completed: true
        }
      ],
      effortLevel: 8,
      notes: "Buen día de entrenamiento",
      trainerFeedback: null
    }
  ]
}
```

**ARCHIVOS AFECTADOS:**
- `src/main/java/org/example/polyglotdataproyect/entities/ProgressEntry.java` ⚠️
- `src/main/java/org/example/polyglotdataproyect/services/ProgressService.java` ✅
- `src/main/java/org/example/polyglotdataproyect/controller/mvc/ProgressMvcController.java` ⚠️
- `src/main/resources/templates/progress/detail.html` ❌

**PRIORIDAD:** 🟡 MEDIA (funcional pero modelo simplificado)

---

### 5. FUNCIONALIDADES DEL ENTRENADOR

#### Requerimiento del PDF:
> "Los entrenadores deben poder visualizar las rutinas y el progreso de los estudiantes o colaboradores que tengan asignados y realizar recomendaciones según el avance que tenga el usuario. También deben poder subir rutinas prediseñadas para que los usuarios las consulten y adopten, así como registrar nuevos ejercicios en el sistema."

#### ✅ IMPLEMENTADO:
- Sistema de asignación trainer-trainee (`TrainerTrainee` entity)
- `TrainerMvcController` con endpoints:
  - Ver usuarios asignados
  - Ver progreso de usuario asignado
  - Ver rutinas de usuario asignado
  - Dar feedback en progreso
- `AssignmentService` para gestionar asignaciones
- Template `trainer/assigned-users.html`
- Template `trainer/user-progress.html`
- Sistema de feedback en progreso implementado

#### ⚠️ PARCIALMENTE IMPLEMENTADO:
- Los entrenadores pueden ver progreso y rutinas
- Pueden dar feedback
- Las rutinas prediseñadas (templates) existen pero falta UI completa

#### ❌ FALTANTE:
1. **Templates faltantes:**
   - `trainer/dashboard.html` - Dashboard principal del entrenador
   - `trainer/user-routines.html` - Ver rutinas de usuario asignado

2. **Control de permisos:**
   - No hay validación de que solo el entrenador asignado vea el usuario
   - Cualquiera puede acceder a `/trainer/*` endpoints

3. **Funcionalidad:**
   - Los entrenadores pueden crear ejercicios pero no hay restricción (todos pueden)
   - Los entrenadores pueden crear rutinas template pero no hay restricción
   - No hay notificación al usuario cuando recibe feedback

4. **UI de asignación:**
   - Falta vista en el dashboard del entrenador mostrando sus asignados
   - Falta estadísticas del entrenador (cantidad de usuarios, feedback dado, etc.)

**ARCHIVOS AFECTADOS:**
- `src/main/java/org/example/polyglotdataproyect/entities/TrainerTrainee.java` ✅
- `src/main/java/org/example/polyglotdataproyect/services/AssignmentService.java` ✅
- `src/main/java/org/example/polyglotdataproyect/controller/mvc/TrainerMvcController.java` ⚠️
- `src/main/resources/templates/trainer/dashboard.html` ❌
- `src/main/resources/templates/trainer/user-routines.html` ❌

**PRIORIDAD:** 🟠 ALTA (funcional backend, falta UI y permisos)

---

### 6. MÓDULO DE ADMINISTRACIÓN

#### Requerimiento del PDF:
> "Debe haber un módulo de administración, donde se puedan asignar un entrenador a un usuario, o modificar su asignación."

#### ✅ IMPLEMENTADO:
- `AdminMvcController` con endpoints completos:
  - Dashboard de administración
  - Asignar entrenador a usuario
  - Ver todas las asignaciones
  - Eliminar asignaciones
  - Migrar datos SQL → MongoDB
- `AdminService` para lógica de negocio
- Templates:
  - `admin/assign-trainer.html`
  - `admin/assignments.html`
  - `admin/migrate-data.html`
  - `dashboard/admin.html`

#### ❌ FALTANTE:
1. **CRÍTICO - Control de permisos:**
   - Cualquier usuario puede acceder a `/admin/*`
   - No hay validación de rol ADMIN

2. **Funcionalidad adicional:**
   - No se pueden editar asignaciones (solo crear y eliminar)
   - No hay gestión de usuarios (crear, editar, desactivar)
   - No hay gestión de entrenadores (listar, estadísticas)
   - No hay auditoria de cambios

**ARCHIVOS AFECTADOS:**
- `src/main/java/org/example/polyglotdataproyect/controller/mvc/AdminMvcController.java` ✅ (sin permisos)
- `src/main/java/org/example/polyglotdataproyect/services/AdminService.java` ✅

**PRIORIDAD:** 🔴 CRÍTICA (funcional pero sin seguridad)

---

### 7. ESTADÍSTICAS EN BASE DE DATOS RELACIONAL

#### Requerimiento del PDF:
> "La Universidad desea que, en la BD relacional, se incluya una tabla con estadísticas, tanto de los usuarios como de los instructores. De los usuarios, se necesita conocer por mes, la cantidad de rutinas que ha iniciado, y la cantidad de veces que ha realizado seguimiento. De los instructores, se requiere la cantidad de usuarios que asignaciones nuevas por mes, y la cantidad de seguimientos que ha realizado en el mes."

#### ❌ COMPLETAMENTE FALTANTE:

**NO EXISTE:**
1. Tabla de estadísticas en PostgreSQL/H2
2. Entidad JPA para estadísticas
3. Repositorio para estadísticas
4. Servicio para calcular/actualizar estadísticas
5. Triggers o procesos para actualizar estadísticas
6. Queries para generar estadísticas

**LO QUE SE DEBE IMPLEMENTAR:**

**Modelo sugerido para tabla SQL:**
```sql
-- Estadísticas de Usuarios
CREATE TABLE USER_STATISTICS (
    id SERIAL PRIMARY KEY,
    user_id VARCHAR(30) REFERENCES USERS(username),
    month INTEGER NOT NULL, -- 1-12
    year INTEGER NOT NULL,
    routines_started INTEGER DEFAULT 0,
    progress_logs_count INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, month, year)
);

-- Estadísticas de Entrenadores
CREATE TABLE TRAINER_STATISTICS (
    id SERIAL PRIMARY KEY,
    trainer_id VARCHAR(30) REFERENCES USERS(username),
    month INTEGER NOT NULL,
    year INTEGER NOT NULL,
    new_assignments_count INTEGER DEFAULT 0,
    feedbacks_given_count INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(trainer_id, month, year)
);
```

**Entidades Java necesarias:**
```java
@Entity
@Table(name = "USER_STATISTICS")
public class UserStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    private Integer month;
    private Integer year;

    @Column(name = "routines_started")
    private Integer routinesStarted = 0;

    @Column(name = "progress_logs_count")
    private Integer progressLogsCount = 0;

    // timestamps...
}

@Entity
@Table(name = "TRAINER_STATISTICS")
public class TrainerStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trainer_id")
    private String trainerId;

    private Integer month;
    private Integer year;

    @Column(name = "new_assignments_count")
    private Integer newAssignmentsCount = 0;

    @Column(name = "feedbacks_given_count")
    private Integer feedbacksGivenCount = 0;

    // timestamps...
}
```

**Servicios necesarios:**
- `StatisticsService` para actualizar estadísticas cuando:
  - Un usuario crea una rutina → incrementar `routines_started`
  - Un usuario registra progreso → incrementar `progress_logs_count`
  - Se asigna un entrenador → incrementar `new_assignments_count`
  - Un entrenador da feedback → incrementar `feedbacks_given_count`

**ARCHIVOS A CREAR:**
- `src/main/java/org/example/polyglotdataproyect/entities/UserStatistics.java` ❌
- `src/main/java/org/example/polyglotdataproyect/entities/TrainerStatistics.java` ❌
- `src/main/java/org/example/polyglotdataproyect/repositories/UserStatisticsRepository.java` ❌
- `src/main/java/org/example/polyglotdataproyect/repositories/TrainerStatisticsRepository.java` ❌
- `src/main/java/org/example/polyglotdataproyect/services/StatisticsService.java` ❌
- `src/main/resources/schema.sql` (agregar tablas) ❌

**PRIORIDAD:** 🔴 CRÍTICA (requerimiento explícito del cliente)

---

### 8. INFORMES INNOVADORES

#### Requerimiento del PDF:
> "El cliente desea propuestas innovadoras, para ello tendrá en cuenta que se muestren informes que puedan ser de interés para los usuarios, por lo menos dos informes que tengan valor para el usuario."

#### ⚠️ PARCIALMENTE IMPLEMENTADO:
- En `dashboard/user.html` hay un placeholder para "Activity Heatmap"
- Template tiene sección "Monthly Stats" (pero muy básica)

#### ❌ FALTANTE - PROPUESTAS DE INFORMES INNOVADORES:

**INFORME 1: Heatmap de Actividad (Calendarizado)**
- Mapa de calor mostrando días activos vs inactivos
- Inspirado en GitHub contributions
- Visualización de consistencia del usuario
- Datos: fecha de cada progreso registrado en los últimos 365 días

**INFORME 2: Gráfico de Progreso por Tipo de Ejercicio**
- Gráfico de barras/líneas mostrando evolución temporal
- Comparar cardio vs fuerza vs movilidad
- Identificar áreas de mejora
- Datos: progreso agregado por tipo de ejercicio

**INFORME 3: Comparativa de Rendimiento**
- Comparar progreso del usuario vs promedio de usuarios similares
- Segmentación por campus, programa, edad
- Gamificación con ranking
- Datos: estadísticas agregadas de MongoDB

**INFORME 4: Predicción de Objetivos**
- Usando progreso histórico, predecir cuándo alcanzará objetivo
- Ejemplo: "A este ritmo, completarás 100 entrenamientos en 3 meses"
- Datos: tendencias en progreso del usuario

**INFORME 5: Dashboard del Entrenador**
- Vista de todos los usuarios asignados
- Alertas de usuarios inactivos (>7 días sin progreso)
- Ranking de usuarios más consistentes
- Promedio de esfuerzo de usuarios

**INFORME 6: Análisis de Rutinas Populares**
- Rutinas más copiadas
- Rutinas con mejor consistencia de seguimiento
- Recomendaciones basadas en popularidad

**IMPLEMENTACIÓN SUGERIDA:**
- Usar Chart.js o D3.js para visualizaciones
- Crear endpoint `/api/reports/{reportType}` para datos JSON
- Agregar sección "Reports" en navbar
- Templates en `templates/reports/`

**ARCHIVOS A CREAR:**
- `src/main/java/org/example/polyglotdataproyect/controller/ReportController.java` ❌
- `src/main/java/org/example/polyglotdataproyect/services/ReportService.java` ❌
- `src/main/resources/templates/reports/activity-heatmap.html` ❌
- `src/main/resources/templates/reports/progress-by-type.html` ❌
- `src/main/resources/static/js/charts.js` ❌

**PRIORIDAD:** 🟡 MEDIA (valor agregado, no bloqueante)

---

## ANÁLISIS DE PERMISOS POR ROL

### ROL: ESTUDIANTE (STUDENT)

#### ✅ DEBE PODER:
- ✅ Ver su propio dashboard
- ✅ Crear/editar/eliminar sus propias rutinas
- ✅ Ver biblioteca de ejercicios
- ⚠️ Crear ejercicios personalizados (implementado pero sin restricción)
- ✅ Copiar rutinas prediseñadas
- ⚠️ Editar rutinas copiadas (implementado pero sin validación)
- ✅ Registrar su progreso
- ✅ Ver su propio progreso
- ⚠️ Ver feedback del entrenador (implementado pero sin notificación)

#### ❌ NO DEBE PODER:
- ❌ Ver dashboard de otros usuarios (ACTUALMENTE PUEDE)
- ❌ Editar rutinas de otros (ACTUALMENTE PUEDE)
- ❌ Registrar progreso de otros (ACTUALMENTE PUEDE)
- ❌ Acceder a módulo de administración (ACTUALMENTE PUEDE)
- ❌ Ver módulo de entrenador (ACTUALMENTE PUEDE)
- ❌ Crear ejercicios públicos (solo entrenadores)

### ROL: COLABORADOR (EMPLOYEE)

#### Permisos = ESTUDIANTE
- Mismo nivel de acceso que estudiantes
- Implementado correctamente en cuanto a funcionalidad
- Falta control de acceso

### ROL: ENTRENADOR (TRAINER)

#### ✅ DEBE PODER:
- ✅ Todo lo que puede un estudiante/colaborador
- ✅ Ver usuarios asignados
- ✅ Ver progreso de usuarios asignados
- ✅ Ver rutinas de usuarios asignados
- ✅ Dar feedback en progreso de usuarios asignados
- ⚠️ Crear ejercicios públicos (implementado sin restricción)
- ⚠️ Crear rutinas prediseñadas (implementado sin restricción)
- ❌ Ver estadísticas de sus usuarios (NO IMPLEMENTADO)

#### ❌ NO DEBE PODER:
- ❌ Ver progreso de usuarios NO asignados (ACTUALMENTE PUEDE)
- ❌ Acceder a módulo de administración (ACTUALMENTE PUEDE)
- ❌ Asignar/desasignar usuarios (solo admin)

### ROL: ADMINISTRADOR (ADMIN)

#### ✅ DEBE PODER:
- ✅ Asignar entrenadores a usuarios
- ✅ Ver todas las asignaciones
- ✅ Eliminar asignaciones
- ⚠️ Migrar datos SQL → MongoDB (implementado pero sin validación)
- ❌ Gestionar usuarios (NO IMPLEMENTADO)
- ❌ Ver estadísticas globales (NO IMPLEMENTADO)
- ❌ Gestionar ejercicios públicos (NO IMPLEMENTADO)
- ❌ Gestionar rutinas prediseñadas (NO IMPLEMENTADO)

#### ✅ TIENE ACCESO PERO SIN RESTRICCIÓN:
- ACTUALMENTE: cualquiera puede acceder a `/admin/*`

---

## CHECKLIST DE IMPLEMENTACIÓN DE SEGURIDAD

### 1. Spring Security Configuration

#### A IMPLEMENTAR:
```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        return http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login", "/css/**", "/js/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/trainer/**").hasAnyRole("TRAINER", "ADMIN")
                .requestMatchers("/dashboard/**").authenticated()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/auth/login")
                .defaultSuccessUrl("/dashboard")
                .failureUrl("/auth/login?error=true")
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/auth/login")
            )
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

### 2. UserDetailsService Implementation

```java
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null || !user.getIsActive()) {
            throw new UsernameNotFoundException("User not found");
        }

        return org.springframework.security.core.userdetails.User
            .withUsername(user.getUsername())
            .password(user.getPasswordHash())
            .roles(user.getRole())
            .build();
    }
}
```

### 3. Controllers con @PreAuthorize

```java
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminMvcController {
    // ...
}

@Controller
@RequestMapping("/trainer")
@PreAuthorize("hasAnyRole('TRAINER', 'ADMIN')")
public class TrainerMvcController {
    // ...
}
```

### 4. Ownership Validation en Services

```java
@Service
public class RoutineService {

    public Routine updateRoutine(String id, Routine routine, String currentUserId) {
        Optional<Routine> existing = routineRepository.findById(id);
        if (existing.isEmpty()) {
            throw new NotFoundException("Routine not found");
        }

        // Validar ownership
        if (!existing.get().getOwnerId().equals(currentUserId)) {
            throw new ForbiddenException("You can only edit your own routines");
        }

        routine.setId(id);
        return routineRepository.save(routine);
    }
}
```

### 5. Navbar con Role-Based Visibility

```html
<!-- navbar.html -->
<nav>
    <a href="/dashboard">Dashboard</a>
    <a href="/exercises">Exercises</a>
    <a href="/routines">My Routines</a>
    <a href="/progress">My Progress</a>

    <!-- Solo para Trainers -->
    <a th:if="${#authorization.expression('hasAnyRole(''TRAINER'', ''ADMIN'')')}"
       href="/trainer/dashboard">Trainer Panel</a>

    <!-- Solo para Admins -->
    <a th:if="${#authorization.expression('hasRole(''ADMIN'')')}"
       href="/admin">Admin Panel</a>

    <a href="/logout">Logout</a>
</nav>
```

---

## RESUMEN DE PRIORIDADES

### 🔴 PRIORIDAD CRÍTICA (BLOQUEANTE)

1. **Implementar Spring Security**
   - Autenticación basada en sesión
   - Password hashing (BCrypt)
   - Logout funcional
   - Estimado: 8-12 horas

2. **Control de Acceso por Roles**
   - @PreAuthorize en controladores
   - Filtros de seguridad
   - Validación de ownership en servicios
   - Estimado: 6-10 horas

3. **Tabla de Estadísticas en SQL**
   - Crear entidades UserStatistics y TrainerStatistics
   - Crear repositorios
   - Implementar StatisticsService
   - Triggers/listeners para actualizar automáticamente
   - Estimado: 10-15 horas

4. **Actualizar Contraseñas Existentes**
   - Hashear contraseñas en data.sql
   - Script de migración para usuarios existentes
   - Estimado: 2-3 horas

**TOTAL CRÍTICO: 26-40 horas**

---

### 🟠 PRIORIDAD ALTA (FUNCIONALIDAD IMPORTANTE)

1. **Templates Faltantes**
   - trainer/dashboard.html
   - trainer/user-routines.html
   - exercises/view.html
   - exercises/edit.html
   - routines/list.html
   - routines/edit.html
   - routines/templates.html
   - progress/detail.html
   - Estimado: 12-16 horas

2. **Validación de Ownership**
   - Usuarios solo pueden editar sus propias rutinas
   - Usuarios solo pueden registrar su propio progreso
   - Entrenadores solo ven usuarios asignados
   - Estimado: 6-8 horas

3. **Navbar Basado en Roles**
   - Mostrar/ocultar opciones según rol
   - Usar Thymeleaf Security
   - Estimado: 2-3 horas

**TOTAL ALTA: 20-27 horas**

---

### 🟡 PRIORIDAD MEDIA (MEJORAS Y VALOR AGREGADO)

1. **Informes Innovadores (mínimo 2)**
   - Activity Heatmap
   - Gráfico de progreso por tipo
   - Estimado: 15-20 horas

2. **Mejorar Modelo de Progreso**
   - Registrar progreso por ejercicio individual
   - Incluir sets, reps, peso, tiempo
   - Estimado: 8-10 horas

3. **Seed Data**
   - Ejercicios predefinidos (20-30)
   - Rutinas prediseñadas (5-10)
   - Estimado: 4-6 horas

4. **Funcionalidad de Edición de Rutinas Copiadas**
   - Permitir ajustes a rutinas copiadas
   - Mantener relación copiedFrom
   - Estimado: 3-4 horas

**TOTAL MEDIA: 30-40 horas**

---

### 🟢 PRIORIDAD BAJA (NICE TO HAVE)

1. **Notificaciones**
   - Notificar al usuario cuando recibe feedback
   - Notificar al entrenador cuando hay nuevo progreso
   - Estimado: 8-12 horas

2. **Dashboard del Entrenador Mejorado**
   - Estadísticas de usuarios asignados
   - Alertas de inactividad
   - Ranking de usuarios
   - Estimado: 10-15 horas

3. **Gestión Completa de Usuarios (Admin)**
   - Crear/editar/desactivar usuarios
   - Cambiar contraseñas
   - Auditoría de cambios
   - Estimado: 8-10 horas

4. **Recuperación de Contraseña**
   - Reset password por email
   - Estimado: 6-8 horas

**TOTAL BAJA: 32-45 horas**

---

## ESTIMACIÓN TOTAL DEL PROYECTO

### Para completar TODOS los requerimientos:
- **Crítico:** 26-40 horas
- **Alto:** 20-27 horas
- **Medio:** 30-40 horas
- **Bajo:** 32-45 horas

**TOTAL: 108-152 horas (~3-4 semanas de trabajo full-time)**

### Para cumplir MÍNIMO viable:
- **Crítico:** 26-40 horas
- **Alto (solo templates críticos):** 8-12 horas
- **Medio (2 informes básicos):** 15-20 horas

**TOTAL MÍNIMO: 49-72 horas (~1-2 semanas)**

---

## RECOMENDACIONES FINALES

### Orden de Implementación Sugerido:

1. **SPRINT 1 (Seguridad) - 26-40 horas**
   - Implementar Spring Security
   - Hashear contraseñas
   - Control de acceso por roles
   - Validación de ownership

2. **SPRINT 2 (Estadísticas) - 10-15 horas**
   - Tabla de estadísticas en SQL
   - Entidades y repositorios
   - StatisticsService
   - Triggers automáticos

3. **SPRINT 3 (UI) - 15-20 horas**
   - Templates faltantes críticos
   - Navbar basado en roles
   - 2 informes innovadores básicos

4. **SPRINT 4 (Mejoras) - 20-30 horas**
   - Seed data
   - Mejorar modelo de progreso
   - Funcionalidades adicionales

### Estado Actual vs Objetivo:

| Componente | Estado Actual | Objetivo | Gap |
|------------|---------------|----------|-----|
| Base de Datos | 100% | 100% | 0% |
| Backend Logic | 70% | 100% | 30% |
| Seguridad | 0% | 100% | 100% |
| UI/Templates | 60% | 100% | 40% |
| Estadísticas SQL | 0% | 100% | 100% |
| Informes | 10% | 100% | 90% |
| **TOTAL** | **40%** | **100%** | **60%** |

---

**CONCLUSIÓN:**

El proyecto tiene una base sólida con la arquitectura de datos polyglot bien implementada y la mayoría de la lógica de negocio funcional. Sin embargo, tiene dos deficiencias críticas que deben abordarse de inmediato:

1. **Falta total de seguridad y control de acceso**
2. **Ausencia de la tabla de estadísticas requerida en SQL**

Estas dos deficiencias son bloqueantes para la entrega del proyecto. Una vez resueltas, el resto son principalmente mejoras de UI y funcionalidades adicionales que agregan valor pero no impiden el funcionamiento básico del sistema.

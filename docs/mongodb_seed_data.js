// ============================================
// MONGODB SEED DATA - Polyglot Fitness App
// ============================================
// Para ejecutar este script:
// 1. Conectarse a MongoDB: mongosh
// 2. Seleccionar base de datos: use polyglotdb
// 3. Ejecutar: load('/ruta/a/mongodb_seed_data.js')
// ============================================

usepolyglotdb;

// Limpiar colecciones existentes (opcional - descomentar si quieres empezar limpio)
// db.exercises.deleteMany({});
// db.routines.deleteMany({});
// db.progress.deleteMany({});

print("=== Insertando Ejercicios Predefinidos ===");

// 1. EJERCICIOS PREDEFINIDOS (creados por entrenadores)
const exercises = db.exercises.insertMany([
    // Ejercicios de Fuerza - Paula Ramírez
    {
        name: "Sentadillas",
        type: "fuerza",
        description: "Ejercicio fundamental para fortalecer piernas y glúteos. Mantén la espalda recta y baja hasta que los muslos estén paralelos al suelo.",
        duration: 15,
        difficulty: "intermedio",
        videos: [
            "https://www.youtube.com/watch?v=aclHkVaku9U",
            "https://www.youtube.com/watch?v=SW_C1A-rejs"
        ],
        isPredefined: true,
        createdByUsername: "paula.r"
    },
    {
        name: "Flexiones de Pecho",
        type: "fuerza",
        description: "Ejercicio clásico para pecho, hombros y tríceps. Mantén el cuerpo recto como una tabla.",
        duration: 10,
        difficulty: "intermedio",
        videos: [
            "https://www.youtube.com/watch?v=IODxDxX7oi4"
        ],
        isPredefined: true,
        createdByUsername: "paula.r"
    },
    {
        name: "Plancha Abdominal",
        type: "fuerza",
        description: "Ejercicio isométrico para fortalecer el core. Mantén el cuerpo recto y contraído.",
        duration: 5,
        difficulty: "facil",
        videos: [
            "https://www.youtube.com/watch?v=ASdvN_XEl_c"
        ],
        isPredefined: true,
        createdByUsername: "paula.r"
    },
    {
        name: "Dominadas",
        type: "fuerza",
        description: "Ejercicio avanzado para espalda y bíceps. Usa banda de asistencia si eres principiante.",
        duration: 12,
        difficulty: "dificil",
        videos: [
            "https://www.youtube.com/watch?v=eGo4IYlbE5g"
        ],
        isPredefined: true,
        createdByUsername: "andres.c"
    },

    // Ejercicios de Cardio - Andrés Castro
    {
        name: "Trote Suave",
        type: "cardio",
        description: "Carrera a ritmo moderado. Ideal para calentar o como ejercicio cardiovascular principal.",
        duration: 20,
        difficulty: "facil",
        videos: [
            "https://www.youtube.com/watch?v=8RvAzJkS4zg"
        ],
        isPredefined: true,
        createdByUsername: "andres.c"
    },
    {
        name: "Burpees",
        type: "cardio",
        description: "Ejercicio de cuerpo completo de alta intensidad. Combina sentadilla, plancha y salto.",
        duration: 10,
        difficulty: "dificil",
        videos: [
            "https://www.youtube.com/watch?v=TU8QYVW0gDU"
        ],
        isPredefined: true,
        createdByUsername: "andres.c"
    },
    {
        name: "Saltos de Tijera (Jumping Jacks)",
        type: "cardio",
        description: "Ejercicio cardiovascular básico. Perfecto para calentamiento.",
        duration: 8,
        difficulty: "facil",
        videos: [
            "https://www.youtube.com/watch?v=c4DAnQ6DtF8"
        ],
        isPredefined: true,
        createdByUsername: "paula.r"
    },

    // Ejercicios de Movilidad
    {
        name: "Estiramiento de Piernas",
        type: "movilidad",
        description: "Estiramientos suaves para mejorar flexibilidad en piernas y cadera.",
        duration: 10,
        difficulty: "facil",
        videos: [
            "https://www.youtube.com/watch?v=g_tea8ZNk5A"
        ],
        isPredefined: true,
        createdByUsername: "paula.r"
    },
    {
        name: "Yoga Básico",
        type: "movilidad",
        description: "Secuencia de posturas básicas de yoga para mejorar flexibilidad y relajación.",
        duration: 30,
        difficulty: "facil",
        videos: [
            "https://www.youtube.com/watch?v=v7AYKMP6rOE"
        ],
        isPredefined: true,
        createdByUsername: "andres.c"
    }
]);

print(`Ejercicios insertados: ${exercises.insertedIds.length}`);

// Guardar IDs de ejercicios para usar en rutinas
const exerciseIds = Object.values(exercises.insertedIds).map(id => id.toString());

print("=== Insertando Rutinas Prediseñadas ===");

// 2. RUTINAS PREDISEÑADAS (creadas por entrenadores)
const routines = db.routines.insertMany([
    {
        name: "Rutina Full Body para Principiantes",
        description: "Rutina completa de cuerpo completo diseñada para personas que inician en el gimnasio. Combina ejercicios de fuerza y cardio. Duración total: ~60 minutos.",
        username: "paula.r",
        exerciseIds: [
            exerciseIds[6], // Saltos de Tijera (calentamiento)
            exerciseIds[0], // Sentadillas
            exerciseIds[1], // Flexiones
            exerciseIds[2], // Plancha
            exerciseIds[4], // Trote Suave
            exerciseIds[7]  // Estiramiento de Piernas
        ],
        isPredefined: true,
        createdByTrainer: "paula.r",
        originalRoutineId: null,
        createdAt: new Date(),
        updatedAt: new Date()
    },
    {
        name: "Rutina HIIT Avanzada",
        description: "Entrenamiento de alta intensidad por intervalos. Solo para personas con buena condición física. Duración total: ~35 minutos.",
        username: "andres.c",
        exerciseIds: [
            exerciseIds[5], // Burpees
            exerciseIds[3], // Dominadas
            exerciseIds[0], // Sentadillas
            exerciseIds[1], // Flexiones
            exerciseIds[7]  // Estiramiento
        ],
        isPredefined: true,
        createdByTrainer: "andres.c",
        originalRoutineId: null,
        createdAt: new Date(),
        updatedAt: new Date()
    },
    {
        name: "Rutina de Movilidad y Flexibilidad",
        description: "Rutina enfocada en mejorar movilidad y reducir tensión muscular. Ideal para días de recuperación. Duración total: ~40 minutos.",
        username: "paula.r",
        exerciseIds: [
            exerciseIds[7], // Estiramiento de Piernas
            exerciseIds[8], // Yoga Básico
            exerciseIds[2]  // Plancha (para core)
        ],
        isPredefined: true,
        createdByTrainer: "paula.r",
        originalRoutineId: null,
        createdAt: new Date(),
        updatedAt: new Date()
    },
    {
        name: "Rutina Cardio Moderado",
        description: "Rutina cardiovascular para mejorar resistencia aeróbica. Perfecta para quemar calorías. Duración total: ~45 minutos.",
        username: "andres.c",
        exerciseIds: [
            exerciseIds[6], // Saltos de Tijera
            exerciseIds[4], // Trote Suave
            exerciseIds[5], // Burpees
            exerciseIds[7]  // Estiramiento
        ],
        isPredefined: true,
        createdByTrainer: "andres.c",
        originalRoutineId: null,
        createdAt: new Date(),
        updatedAt: new Date()
    }
]);

print(`Rutinas prediseñadas insertadas: ${routines.insertedIds.length}`);

// Guardar IDs de rutinas
const routineIds = Object.values(routines.insertedIds).map(id => id.toString());

print("=== Insertando Rutinas Personales de Estudiantes (ejemplos) ===");

// 3. RUTINAS PERSONALES (algunas adoptadas de predefinidas)
db.routines.insertMany([
    {
        name: "Rutina Full Body para Principiantes (Copia)",
        description: "Rutina completa de cuerpo completo diseñada para personas que inician en el gimnasio. Combina ejercicios de fuerza y cardio. Duración total: ~60 minutos.",
        username: "laura.h",
        exerciseIds: [
            exerciseIds[6], // Saltos de Tijera
            exerciseIds[0], // Sentadillas
            exerciseIds[1], // Flexiones
            exerciseIds[7]  // Estiramiento (modificó: quitó algunos ejercicios)
        ],
        isPredefined: false,
        createdByTrainer: null,
        originalRoutineId: routineIds[0], // Adoptada de la rutina de Paula
        createdAt: new Date(),
        updatedAt: new Date()
    },
    {
        name: "Mi Rutina Matutina",
        description: "Rutina personalizada para hacer en las mañanas antes de clase",
        username: "pedro.m",
        exerciseIds: [
            exerciseIds[6], // Saltos de Tijera
            exerciseIds[2], // Plancha
            exerciseIds[7]  // Estiramiento
        ],
        isPredefined: false,
        createdByTrainer: null,
        originalRoutineId: null,
        createdAt: new Date(),
        updatedAt: new Date()
    }
]);

print("Rutinas personales insertadas: 2");

print("=== Insertando Registros de Progreso ===");

// 4. PROGRESO (ejemplos de seguimiento de estudiantes)
db.progress.insertMany([
    {
        username: "laura.h",
        routineId: routineIds[0],
        progressDate: new Date("2025-11-01"),
        metrics: "repeticiones:15, tiempo:55min, esfuerzo:medio",
        notes: "Primera vez haciendo esta rutina. Me costó un poco pero la completé.",
        trainerRecommendation: "Excelente inicio Laura! Para la próxima semana intenta aumentar a 20 repeticiones en sentadillas.",
        trainerUsername: "paula.r"
    },
    {
        username: "laura.h",
        routineId: routineIds[0],
        progressDate: new Date("2025-11-04"),
        metrics: "repeticiones:18, tiempo:50min, esfuerzo:medio",
        notes: "Hoy me sentí mejor, más energía!",
        trainerRecommendation: null,
        trainerUsername: null
    },
    {
        username: "pedro.m",
        routineId: routineIds[1],
        progressDate: new Date("2025-11-02"),
        metrics: "repeticiones:10, tiempo:30min, esfuerzo:alto",
        notes: "Muy exigente, tuve que descansar varias veces",
        trainerRecommendation: "Pedro, esta rutina es avanzada. Te recomiendo empezar con la rutina para principiantes.",
        trainerUsername: "andres.c"
    },
    {
        username: "ana.s",
        routineId: routineIds[2],
        progressDate: new Date("2025-11-03"),
        metrics: "tiempo:40min, esfuerzo:bajo",
        notes: "Me encantó! Me siento mucho más relajada",
        trainerRecommendation: null,
        trainerUsername: null
    }
]);

print("Progreso insertado: 4 registros");

print("\n=== RESUMEN ===");
print(`Total Ejercicios: ${db.exercises.countDocuments()}`);
print(`Total Rutinas: ${db.routines.countDocuments()}`);
print(`Total Progreso: ${db.progress.countDocuments()}`);
print("\n=== DATOS IMPORTANTES ===");
print("Entrenadores: paula.r, andres.c");
print("Estudiantes: laura.h, pedro.m, ana.s, luis.r, sofia.g");
print("Password para todos: password123");
print("\n=== SEED COMPLETADO ===");

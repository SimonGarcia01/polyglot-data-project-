// ============================================
// MONGODB SEED DATA - Polyglot Fitness App
// ============================================
// Para ejecutar este script:
// 1. Conectarse a MongoDB: mongosh
// 2. Seleccionar base de datos: use polyglotdb
// 3. Ejecutar: load('/ruta/a/mongodb_seed_data.js')
// ============================================

db = db.getSiblingDB('polyglotdb');

// Limpiar colecciones existentes (opcional - descomentar si quieres empezar limpio)
// db.exercises.deleteMany({});
// db.routines.deleteMany({});
// db.progress_entry.deleteMany({});

print("=== Insertando Ejercicios Predefinidos ===");

// 1. EJERCICIOS PREDEFINIDOS (creados por entrenadores)
// Nota: createdBy debe ser el userId del SQL (2003, 2004, etc.)
const exercises = db.exercises.insertMany([
    // Ejercicios de Fuerza - Paula Ramírez (trainerId: 2003)
    {
        name: "Sentadillas",
        type: "fuerza",
        description: "Ejercicio fundamental para fortalecer piernas y glúteos. Mantén la espalda recta y baja hasta que los muslos estén paralelos al suelo.",
        duration_min: 15,
        difficulty: "intermediate",
        video_url: "https://www.youtube.com/watch?v=aclHkVaku9U",
        created_by: "2003"
    },
    {
        name: "Flexiones de Pecho",
        type: "fuerza",
        description: "Ejercicio clásico para pecho, hombros y tríceps. Mantén el cuerpo recto como una tabla.",
        duration_min: 10,
        difficulty: "intermediate",
        video_url: "https://www.youtube.com/watch?v=IODxDxX7oi4",
        created_by: "2003"
    },
    {
        name: "Plancha Abdominal",
        type: "fuerza",
        description: "Ejercicio isométrico para fortalecer el core. Mantén el cuerpo recto y contraído.",
        duration_min: 5,
        difficulty: "beginner",
        video_url: "https://www.youtube.com/watch?v=ASdvN_XEl_c",
        created_by: "2003"
    },
    {
        name: "Dominadas",
        type: "fuerza",
        description: "Ejercicio avanzado para espalda y bíceps. Usa banda de asistencia si eres principiante.",
        duration_min: 12,
        difficulty: "advanced",
        video_url: "https://www.youtube.com/watch?v=eGo4IYlbE5g",
        created_by: "2004"
    },
    {
        name: "Zancadas",
        type: "fuerza",
        description: "Ejercicio unilateral para piernas. Alterna las piernas para equilibrio muscular.",
        duration_min: 12,
        difficulty: "intermediate",
        video_url: "https://www.youtube.com/watch?v=QOVaHwm-Q6U",
        created_by: "2003"
    },
    {
        name: "Press de Hombros",
        type: "fuerza",
        description: "Ejercicio para desarrollar hombros fuertes. Puede hacerse con mancuernas o barra.",
        duration_min: 10,
        difficulty: "intermediate",
        video_url: "https://www.youtube.com/watch?v=qEwKCR5JCog",
        created_by: "2004"
    },

    // Ejercicios de Cardio - Andrés Castro (trainerId: 2004)
    {
        name: "Trote Suave",
        type: "cardio",
        description: "Carrera a ritmo moderado. Ideal para calentar o como ejercicio cardiovascular principal.",
        duration_min: 20,
        difficulty: "beginner",
        video_url: "https://www.youtube.com/watch?v=8RvAzJkS4zg",
        created_by: "2004"
    },
    {
        name: "Burpees",
        type: "cardio",
        description: "Ejercicio de cuerpo completo de alta intensidad. Combina sentadilla, plancha y salto.",
        duration_min: 10,
        difficulty: "advanced",
        video_url: "https://www.youtube.com/watch?v=TU8QYVW0gDU",
        created_by: "2004"
    },
    {
        name: "Saltos de Tijera (Jumping Jacks)",
        type: "cardio",
        description: "Ejercicio cardiovascular básico. Perfecto para calentamiento.",
        duration_min: 8,
        difficulty: "beginner",
        video_url: "https://www.youtube.com/watch?v=c4DAnQ6DtF8",
        created_by: "2003"
    },
    {
        name: "Mountain Climbers",
        type: "cardio",
        description: "Ejercicio cardiovascular intenso que también trabaja el core. Simula escalar una montaña.",
        duration_min: 8,
        difficulty: "intermediate",
        video_url: "https://www.youtube.com/watch?v=nmwgirgXLYM",
        created_by: "2004"
    },
    {
        name: "Saltar la Cuerda",
        type: "cardio",
        description: "Ejercicio cardiovascular clásico. Excelente para resistencia y coordinación.",
        duration_min: 15,
        difficulty: "intermediate",
        video_url: "https://www.youtube.com/watch?v=FJmRQ5iTXKE",
        created_by: "2003"
    },

    // Ejercicios de Movilidad
    {
        name: "Estiramiento de Piernas",
        type: "movilidad",
        description: "Estiramientos suaves para mejorar flexibilidad en piernas y cadera.",
        duration_min: 10,
        difficulty: "beginner",
        video_url: "https://www.youtube.com/watch?v=g_tea8ZNk5A",
        created_by: "2003"
    },
    {
        name: "Yoga Básico",
        type: "movilidad",
        description: "Secuencia de posturas básicas de yoga para mejorar flexibilidad y relajación.",
        duration_min: 30,
        difficulty: "beginner",
        video_url: "https://www.youtube.com/watch?v=v7AYKMP6rOE",
        created_by: "2004"
    },
    {
        name: "Estiramiento de Espalda",
        type: "movilidad",
        description: "Rutina de estiramientos para aliviar tensión en la espalda baja y alta.",
        duration_min: 12,
        difficulty: "beginner",
        video_url: "https://www.youtube.com/watch?v=4BOTvaRaDjI",
        created_by: "2003"
    },
    {
        name: "Movilidad de Cadera",
        type: "movilidad",
        description: "Ejercicios para mejorar el rango de movimiento de la cadera. Previene lesiones.",
        duration_min: 15,
        difficulty: "intermediate",
        video_url: "https://www.youtube.com/watch?v=nLuvQCTPrcY",
        created_by: "2004"
    },
    {
        name: "Foam Rolling",
        type: "movilidad",
        description: "Técnica de auto-masaje con rodillo de espuma. Ideal para recuperación muscular.",
        duration_min: 10,
        difficulty: "beginner",
        video_url: "https://www.youtube.com/watch?v=Kp_i8bGhsME",
        created_by: "2003"
    }
]);

print(`✅ Ejercicios insertados: ${exercises.insertedIds.length}`);

// Guardar IDs de ejercicios para usar en rutinas
const exerciseIds = Object.values(exercises.insertedIds);

print("=== Insertando Rutinas Prediseñadas (Templates) ===");

// 2. RUTINAS PREDISEÑADAS (creadas por entrenadores como templates)
// Nota: owner_id es el trainerId, is_template = true
const routines = db.routines.insertMany([
    {
        name: "Rutina Full Body para Principiantes",
        owner_id: "2003", // Paula Ramírez
        is_template: true,
        copied_from: null,
        exercises: [
            {
                exercise_id: exerciseIds[8].toString(), // Saltos de Tijera (calentamiento)
                sets: 3,
                reps: 20,
                duration_min: 5
            },
            {
                exercise_id: exerciseIds[0].toString(), // Sentadillas
                sets: 3,
                reps: 15,
                duration_min: null
            },
            {
                exercise_id: exerciseIds[1].toString(), // Flexiones
                sets: 3,
                reps: 10,
                duration_min: null
            },
            {
                exercise_id: exerciseIds[2].toString(), // Plancha
                sets: 3,
                reps: null,
                duration_min: 1 // 1 minuto cada serie
            },
            {
                exercise_id: exerciseIds[6].toString(), // Trote Suave
                sets: 1,
                reps: null,
                duration_min: 15
            },
            {
                exercise_id: exerciseIds[11].toString(), // Estiramiento de Piernas
                sets: 1,
                reps: null,
                duration_min: 8
            }
        ],
        created_at: new Date("2025-10-15T10:00:00Z")
    },
    {
        name: "Rutina HIIT Avanzada",
        owner_id: "2004", // Andrés Castro
        is_template: true,
        copied_from: null,
        exercises: [
            {
                exercise_id: exerciseIds[8].toString(), // Saltos de Tijera (calentamiento)
                sets: 2,
                reps: 30,
                duration_min: 3
            },
            {
                exercise_id: exerciseIds[7].toString(), // Burpees
                sets: 4,
                reps: 15,
                duration_min: null
            },
            {
                exercise_id: exerciseIds[3].toString(), // Dominadas
                sets: 4,
                reps: 8,
                duration_min: null
            },
            {
                exercise_id: exerciseIds[9].toString(), // Mountain Climbers
                sets: 4,
                reps: 30,
                duration_min: null
            },
            {
                exercise_id: exerciseIds[0].toString(), // Sentadillas
                sets: 4,
                reps: 20,
                duration_min: null
            },
            {
                exercise_id: exerciseIds[13].toString(), // Estiramiento de Espalda
                sets: 1,
                reps: null,
                duration_min: 10
            }
        ],
        created_at: new Date("2025-10-18T14:00:00Z")
    },
    {
        name: "Rutina de Movilidad y Flexibilidad",
        owner_id: "2003", // Paula Ramírez
        is_template: true,
        copied_from: null,
        exercises: [
            {
                exercise_id: exerciseIds[11].toString(), // Estiramiento de Piernas
                sets: 2,
                reps: null,
                duration_min: 8
            },
            {
                exercise_id: exerciseIds[12].toString(), // Yoga Básico
                sets: 1,
                reps: null,
                duration_min: 25
            },
            {
                exercise_id: exerciseIds[13].toString(), // Estiramiento de Espalda
                sets: 2,
                reps: null,
                duration_min: 10
            },
            {
                exercise_id: exerciseIds[14].toString(), // Movilidad de Cadera
                sets: 2,
                reps: null,
                duration_min: 12
            }
        ],
        created_at: new Date("2025-10-20T09:00:00Z")
    },
    {
        name: "Rutina Cardio Moderado",
        owner_id: "2004", // Andrés Castro
        is_template: true,
        copied_from: null,
        exercises: [
            {
                exercise_id: exerciseIds[8].toString(), // Saltos de Tijera
                sets: 3,
                reps: 25,
                duration_min: 5
            },
            {
                exercise_id: exerciseIds[10].toString(), // Saltar la Cuerda
                sets: 3,
                reps: null,
                duration_min: 5
            },
            {
                exercise_id: exerciseIds[6].toString(), // Trote Suave
                sets: 1,
                reps: null,
                duration_min: 20
            },
            {
                exercise_id: exerciseIds[9].toString(), // Mountain Climbers
                sets: 3,
                reps: 25,
                duration_min: null
            },
            {
                exercise_id: exerciseIds[11].toString(), // Estiramiento
                sets: 1,
                reps: null,
                duration_min: 8
            }
        ],
        created_at: new Date("2025-10-22T11:00:00Z")
    },
    {
        name: "Rutina de Fuerza Intermedia",
        owner_id: "2003", // Paula Ramírez
        is_template: true,
        copied_from: null,
        exercises: [
            {
                exercise_id: exerciseIds[8].toString(), // Calentamiento
                sets: 2,
                reps: 20,
                duration_min: 3
            },
            {
                exercise_id: exerciseIds[0].toString(), // Sentadillas
                sets: 4,
                reps: 12,
                duration_min: null
            },
            {
                exercise_id: exerciseIds[4].toString(), // Zancadas
                sets: 3,
                reps: 12,
                duration_min: null
            },
            {
                exercise_id: exerciseIds[1].toString(), // Flexiones
                sets: 4,
                reps: 15,
                duration_min: null
            },
            {
                exercise_id: exerciseIds[5].toString(), // Press de Hombros
                sets: 3,
                reps: 10,
                duration_min: null
            },
            {
                exercise_id: exerciseIds[2].toString(), // Plancha
                sets: 3,
                reps: null,
                duration_min: 2
            },
            {
                exercise_id: exerciseIds[15].toString(), // Foam Rolling
                sets: 1,
                reps: null,
                duration_min: 8
            }
        ],
        created_at: new Date("2025-10-25T10:30:00Z")
    }
]);

print(`✅ Rutinas prediseñadas (templates) insertadas: ${routines.insertedIds.length}`);

// Guardar IDs de rutinas
const routineIds = Object.values(routines.insertedIds);

print("=== Insertando Rutinas Personales de Estudiantes ===");

// 3. RUTINAS PERSONALES (algunas adoptadas de templates, otras creadas desde cero)
// Nota: owner_id es el studentId, is_template = false
db.routines.insertMany([
    {
        name: "Mi Rutina Full Body",
        owner_id: "2001", // Laura Hernández
        is_template: false,
        copied_from: routineIds[0].toString(), // Copiada de template de Paula
        exercises: [
            {
                exercise_id: exerciseIds[8].toString(), // Saltos de Tijera
                sets: 3,
                reps: 20,
                duration_min: 5
            },
            {
                exercise_id: exerciseIds[0].toString(), // Sentadillas
                sets: 3,
                reps: 12, // Redujo reps
                duration_min: null
            },
            {
                exercise_id: exerciseIds[1].toString(), // Flexiones
                sets: 3,
                reps: 8, // Redujo reps
                duration_min: null
            },
            {
                exercise_id: exerciseIds[11].toString(), // Estiramiento
                sets: 1,
                reps: null,
                duration_min: 10
            }
        ],
        created_at: new Date("2025-10-28T08:00:00Z")
    },
    {
        name: "Rutina Matutina Express",
        owner_id: "2002", // Pedro Martínez
        is_template: false,
        copied_from: null, // Creada desde cero
        exercises: [
            {
                exercise_id: exerciseIds[8].toString(), // Saltos de Tijera
                sets: 2,
                reps: 15,
                duration_min: 3
            },
            {
                exercise_id: exerciseIds[2].toString(), // Plancha
                sets: 3,
                reps: null,
                duration_min: 1
            },
            {
                exercise_id: exerciseIds[11].toString(), // Estiramiento
                sets: 1,
                reps: null,
                duration_min: 5
            }
        ],
        created_at: new Date("2025-10-30T07:00:00Z")
    },
    {
        name: "Cardio Quema Grasa",
        owner_id: "3001", // Ana Silva (colaboradora)
        is_template: false,
        copied_from: routineIds[3].toString(), // Copiada de Andrés
        exercises: [
            {
                exercise_id: exerciseIds[10].toString(), // Saltar la Cuerda
                sets: 4,
                reps: null,
                duration_min: 5
            },
            {
                exercise_id: exerciseIds[7].toString(), // Burpees
                sets: 3,
                reps: 10,
                duration_min: null
            },
            {
                exercise_id: exerciseIds[9].toString(), // Mountain Climbers
                sets: 3,
                reps: 20,
                duration_min: null
            }
        ],
        created_at: new Date("2025-11-01T17:00:00Z")
    }
]);

print("✅ Rutinas personales insertadas: 3");

print("=== Insertando Registros de Progreso ===");

// 4. PROGRESO (seguimiento de estudiantes)
// Nota: El modelo usa un documento por usuario+rutina con array de entries
db.progress_entry.insertMany([
    {
        user_id: "2001", // Laura Hernández
        routine_id: routineIds[0].toString(), // Template Full Body
        entries: [
            {
                date: new Date("2025-11-01T09:00:00Z"),
                completed_exercises: 5,
                effort_level: 6,
                trainer_feedback: "Excelente inicio Laura! Para la próxima semana intenta aumentar a 15 repeticiones en sentadillas."
            },
            {
                date: new Date("2025-11-04T09:30:00Z"),
                completed_exercises: 6,
                effort_level: 7,
                trainer_feedback: null
            },
            {
                date: new Date("2025-11-07T10:00:00Z"),
                completed_exercises: 6,
                effort_level: 6,
                trainer_feedback: "Muy bien! Tu consistencia es excelente. Sigue así."
            }
        ]
    },
    {
        user_id: "2002", // Pedro Martínez
        routine_id: routineIds[1].toString(), // HIIT Avanzada
        entries: [
            {
                date: new Date("2025-11-02T14:00:00Z"),
                completed_exercises: 4,
                effort_level: 9,
                trainer_feedback: "Pedro, esta rutina es muy exigente. Te recomiendo empezar con la rutina para principiantes y aumentar gradualmente."
            },
            {
                date: new Date("2025-11-05T14:30:00Z"),
                completed_exercises: 5,
                effort_level: 8,
                trainer_feedback: "Mejoraste! Pero cuida tu técnica en las dominadas."
            }
        ]
    },
    {
        user_id: "3001", // Ana Silva
        routine_id: routineIds[2].toString(), // Movilidad
        entries: [
            {
                date: new Date("2025-11-03T18:00:00Z"),
                completed_exercises: 4,
                effort_level: 3,
                trainer_feedback: null
            },
            {
                date: new Date("2025-11-06T18:30:00Z"),
                completed_exercises: 4,
                effort_level: 3,
                trainer_feedback: "Excelente trabajo Ana! La movilidad es fundamental. Sigue practicando yoga."
            }
        ]
    },
    {
        user_id: "2001", // Laura - su rutina personal
        routine_id: routineIds[5].toString(), // Su copia modificada
        entries: [
            {
                date: new Date("2025-11-08T08:00:00Z"),
                completed_exercises: 4,
                effort_level: 5,
                trainer_feedback: null
            }
        ]
    }
]);

print("✅ Registros de progreso insertados: 4 documentos");

print("\n=== RESUMEN ===");
print(`Total Ejercicios: ${db.exercises.countDocuments()}`);
print(`Total Rutinas: ${db.routines.countDocuments()}`);
print(`Total Progreso: ${db.progress_entry.countDocuments()}`);
print("\n=== DATOS IMPORTANTES ===");
print("Entrenadores (trainers):");
print("  - Paula Ramírez (ID: 2003)");
print("  - Andrés Castro (ID: 2004)");
print("\nEstudiantes:");
print("  - Laura Hernández (ID: 2001)");
print("  - Pedro Martínez (ID: 2002)");
print("\nColaboradores:");
print("  - Ana Silva (ID: 3001)");
print("\nPassword para todos: password123");
print("\n=== ESTRUCTURA DE COLECCIONES ===");
print("exercises: 16 ejercicios (fuerza, cardio, movilidad)");
print("routines: 8 rutinas (5 templates + 3 personales)");
print("progress_entry: 4 documentos de seguimiento");
print("\n✅ SEED COMPLETADO EXITOSAMENTE ===");

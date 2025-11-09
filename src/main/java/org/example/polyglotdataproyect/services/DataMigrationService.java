package org.example.polyglotdataproyect.services;

import org.example.polyglotdataproyect.entities.MongoUser;
import org.example.polyglotdataproyect.entities.User;
import org.example.polyglotdataproyect.repositories.MongoUserRepository;
import org.example.polyglotdataproyect.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class DataMigrationService {

    private static final Logger logger = LoggerFactory.getLogger(DataMigrationService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoUserRepository mongoUserRepository;

    /**
     * Migra todos los usuarios de la base de datos SQL a MongoDB
     * @return Número de usuarios migrados
     */
    @Transactional(readOnly = true)
    public int migrateAllUsersToMongo() {
        logger.info("Iniciando migración de usuarios de SQL a MongoDB...");

        // Obtener todos los usuarios de SQL
        List<User> sqlUsers = userRepository.findAll();
        int migratedCount = 0;
        int skippedCount = 0;
        int errorCount = 0;

        for (User sqlUser : sqlUsers) {
            try {
                // Verificar si el usuario ya existe en MongoDB
                String sqlUserId = extractSqlUserId(sqlUser);

                if (sqlUserId == null) {
                    logger.warn("Usuario {} no tiene Student ni Employee asociado, se omite", sqlUser.getUsername());
                    skippedCount++;
                    continue;
                }

                // Buscar si ya existe en MongoDB
                if (mongoUserRepository.findBySqlUserId(sqlUserId).isPresent()) {
                    logger.debug("Usuario {} ya existe en MongoDB, se omite", sqlUser.getUsername());
                    skippedCount++;
                    continue;
                }

                // Crear nuevo usuario en MongoDB
                MongoUser mongoUser = new MongoUser();
                mongoUser.setSqlUserId(sqlUserId);
                mongoUser.setName(extractName(sqlUser));
                mongoUser.setEmail(extractEmail(sqlUser));
                mongoUser.setRole(mapRoleToMongoRole(sqlUser.getRole()));
                mongoUser.setCreatedAt(new Date());

                mongoUserRepository.save(mongoUser);
                migratedCount++;
                logger.info("Usuario migrado: {} -> {}", sqlUser.getUsername(), mongoUser.getName());

            } catch (Exception e) {
                logger.error("Error al migrar usuario {}: {}", sqlUser.getUsername(), e.getMessage());
                errorCount++;
            }
        }

        logger.info("Migración completada: {} usuarios migrados, {} omitidos, {} errores",
                    migratedCount, skippedCount, errorCount);
        return migratedCount;
    }

    /**
     * Limpia todos los usuarios de MongoDB y ejecuta la migración desde cero
     * @return Número de usuarios migrados
     */
    public int cleanAndMigrate() {
        logger.info("Limpiando usuarios existentes en MongoDB...");
        mongoUserRepository.deleteAll();
        logger.info("Usuarios eliminados. Iniciando migración...");
        return migrateAllUsersToMongo();
    }

    /**
     * Extrae el ID del usuario desde Student o Employee
     */
    private String extractSqlUserId(User sqlUser) {
        if (sqlUser.getStudent() != null) {
            return sqlUser.getStudent().getId();
        } else if (sqlUser.getEmployee() != null) {
            return sqlUser.getEmployee().getId();
        }
        return null;
    }

    /**
     * Extrae el nombre del usuario desde Student o Employee
     */
    private String extractName(User sqlUser) {
        if (sqlUser.getStudent() != null) {
            return sqlUser.getStudent().getFirstName() + " " + sqlUser.getStudent().getLastName();
        } else if (sqlUser.getEmployee() != null) {
            return sqlUser.getEmployee().getFirstName() + " " + sqlUser.getEmployee().getLastName();
        }
        return sqlUser.getUsername();
    }

    /**
     * Extrae el email del usuario desde Student o Employee
     */
    private String extractEmail(User sqlUser) {
        if (sqlUser.getStudent() != null) {
            return sqlUser.getStudent().getEmail();
        } else if (sqlUser.getEmployee() != null) {
            return sqlUser.getEmployee().getEmail();
        }
        return null;
    }

    /**
     * Mapea los roles de SQL a los roles de MongoDB
     */
    private String mapRoleToMongoRole(String sqlRole) {
        if (sqlRole == null) {
            return "student";
        }

        switch (sqlRole.toUpperCase()) {
            case "ADMIN":
            case "TRAINER":
                return "trainer";
            case "STUDENT":
                return "student";
            case "EMPLOYEE":
            case "COLLABORATOR":
                return "collaborator";
            default:
                logger.warn("Rol desconocido: {}, usando 'student' por defecto", sqlRole);
                return "student";
        }
    }
}

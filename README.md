# Security Analysis System

Веб-приложение для анализа защищенности информации ограниченного доступа.

## Что реализовано

- Регистрация и авторизация пользователей
- Роли `ADMIN` и `ANALYST`
- CRUD для объектов защиты (`/assets`)
- CRUD для угроз (`/threats`)
- Расчет риска по формуле `Risk = Probability * Impact`
- Классификация риска: `LOW / MEDIUM / HIGH`
- Рекомендации по снижению риска
- Dashboard с графиками Chart.js (`/dashboard`)
- Отчеты с фильтрацией по дате (`/reports`)
- REST API угроз (`/api/threats`)

## Технологии

- Java 21
- Spring Boot 4
- Spring MVC + Thymeleaf
- Spring Data JPA + Hibernate
- Spring Security
- H2 (по умолчанию для локального запуска)
- PostgreSQL-драйвер подключен как runtime-зависимость

## Быстрый запуск

```bash
./mvnw spring-boot:run
```

После запуска:

- Приложение: `http://localhost:8080/login`
- H2 console: `http://localhost:8080/h2-console`

## Тестовые пользователи

- `admin / admin123` (роль `ADMIN`)
- `analyst / analyst123` (роль `ANALYST`)

## API примеры

- `GET /api/threats` — получить все угрозы
- `POST /api/threats` — создать угрозу
- `DELETE /api/threats/{id}` — удалить угрозу


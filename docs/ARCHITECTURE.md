# Mimari

FlowDesk modüler monolittir. Tek Spring Boot uygulaması REST API, güvenlik, iş kuralları, kalıcılık ve SLA zamanlayıcısını taşır. PostgreSQL tek kalıcı depodur. React SPA, geliştirmede Vite proxy ve Docker'da Nginx reverse proxy ile aynı origin `/api` yolunu kullanır.

## Modüller

- `security`: session/CSRF, kullanıcı ve oturum API'si
- `ticket`: talep, workflow, kuyruk, SLA, audit ve bildirim
- `admin`: yönetici kullanıcı işlemleri
- `core`: ortak konfigürasyon, saat, hata yanıtı ve demo seed

## İstek akışı

React Query → Axios (`/api/v1`) → Nginx/Vite proxy → Spring Security session + CSRF → Controller/DTO doğrulaması → Service transaction ve rol/iş kuralı → Spring Data JPA → PostgreSQL. Kritik değişiklikle audit aynı transaction içinde yazılır.

## Temel tercihler

Session cookie HttpOnly ve CSRF token cookie/header kullanımı seçildi; tarayıcıda kalıcı token yok. PostgreSQL/Flyway seçildi; Hibernate şemayı güncellemez (`validate`). JPA optimistic locking ile son yazan kazanır davranışı engellenir. Mikroservis, Kafka, Redis ve Kubernetes eklenmedi; bu ölçek için gereksiz operasyon yükü yaratırlar.

# Veritabanı

`V1__init.sql` şema ve constraint'leri kurar. Hibernate `ddl-auto=validate` modundadır.

- `users`: kullanıcı adı, ad, BCrypt hash ve aktiflik
- `roles` / `user_roles`: roller ve kullanıcı-rol ilişkisi
- `categories`: talep kategorileri
- `tickets`: talep alanları, SLA son tarihi ve optimistic `version`
- `ticket_comments`: yorumlar için şema tablosu
- `ticket_audit_logs`: değiştirilemeyen uygulama audit olayları
- `notifications`: kullanıcıya özel uygulama içi bildirimler

Queue (`priority, sla_due_at, created_at, id`), durum, atanan, oluşturan, audit ve kullanıcı bildirim sorguları için indeks vardır. SLA ihlali için talep başına tek bildirim partial unique index ile korunur. Geliştirme seed'i kullanıcıları Spring BCrypt encoder ile, 50 talebi tekrarlanabilir `FD-DEMO-*` numaraları ile ekler.

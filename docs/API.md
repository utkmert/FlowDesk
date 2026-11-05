# API özeti

Base path: `/api/v1`. OpenAPI UI: `/swagger-ui.html` (Nginx yolu `/swagger-ui/index.html`). Session tabanlı form login kullanılır; önce `GET /auth/csrf`, sonra form-urlencoded `POST /auth/login` (`username`, `password`). `GET /auth/me`, `POST /auth/logout` oturum yönetir.

| Yöntem | Yol | Yetki/işlev |
|---|---|---|
| GET/POST | `/tickets` | Talep listesi / oluşturma |
| GET | `/tickets/{id}` | Rol/ilişki denetimli detay |
| POST | `/tickets/{id}/assign` | ADMIN, MANAGER; `username` |
| PATCH | `/tickets/{id}/priority` | ADMIN, MANAGER; `priority`, zorunlu `reason` |
| POST | `/tickets/{id}/actions/{START\|SUBMIT\|APPROVE\|REJECT\|CANCEL}` | İş akışı kuralları |
| GET | `/tickets/{id}/audit`, `/audit` | Yönetici/AUDITOR audit |
| GET | `/dashboard` | Dashboard metrikleri |
| GET/POST | `/notifications`, `/notifications/{id}/read` | Kişisel bildirimler |
| GET/POST/PATCH | `/admin/users...` | ADMIN kullanıcı yönetimi |
| GET | `/categories` | Kategorileri arayüzde listeleme |
| POST/PATCH | `/categories...` | ADMIN kategori ekleme/pasifleştirme |

Liste filtreleri: `status`, `priority`, `category`, `assignee`, `q`, `from`, `to` (ISO-8601), `breached`, `page`, `size`. Hata gövdesi `message` taşır; doğrulama 400, yetki 403, eksik kayıt 404, iş kuralı/eşzamanlılık 409 döner.

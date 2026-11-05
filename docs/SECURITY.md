# Güvenlik

- Spring Security session cookie `HttpOnly`, `SameSite=Lax`; CSRF varsayılanı etkin ve Axios XSRF cookie'yi header'a yansıtır.
- Parola BCrypt ile hashlenir. Demo seed yalnızca `APP_DEV_SEED=true` ise çalışır.
- Rol koruması `@PreAuthorize` ile sunucudadır. Çalışan detay/listesi sadece oluşturan/atanan kayıtları alır; bildirim sahibi kontrol edilir.
- Entity'ler doğrudan talep API'sinden çıkmaz; `TicketView` parola hash alanını içermez.
- Bean Validation, SQL parametreleri, hata yanıtlarında genel beklenmeyen hata mesajı ve güvenli aynı-origin proxy kullanılır.
- Audit yazma API'si sunulmaz; audit listesi ADMIN/MANAGER/AUDITOR'a read-only'dir.

Demo sırları yalnızca yerel kullanım içindir. Üretim için HTTPS, güvenli sır yönetimi, hız sınırlama, parola politikası, log saklama politikası ve güvenlik değerlendirmesi gerekir.

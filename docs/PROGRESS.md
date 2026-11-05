# Geliştirme ilerlemesi

## Uygulama planı
1. Gereksinimleri modüllere ayır, veri modelini ve iş kurallarını belirle.
2. Spring Boot, PostgreSQL/Flyway ve React/Vite temelini kur.
3. Session/CSRF güvenliği, API, iş akışı, SLA, audit ve bildirimleri geliştir.
4. Rol tabanlı arayüzü ve API entegrasyonlarını tamamla.
5. Testleri, Docker, CI ve Türkçe dokümantasyonu hazırla; erişilebilir ortamda doğrula.

## Mimari ve kurallar
- Modüler monolit: `ticket`, `security`, `admin`, `core`; React SPA aynı-origin `/api` kullanır.
- PostgreSQL tek kalıcı veri deposudur; şema Flyway migration ile yönetilir.
- OPEN → ASSIGNED → IN_PROGRESS → PENDING_APPROVAL → COMPLETED; ret IN_PROGRESS'e döner. İptal terminaldir.
- Ticket üzerindeki JPA `@Version` çakışmaları 409'a dönüştürülür. SLA süreleri enjekte edilen `Clock` ile takvim saati üzerinden hesaplanır.

## Aşama kayıtları — 2026-11-03
- Çalışma alanı boştu; proje kökünde FlowDesk oluşturuldu. İlk ortam yoklamasında Docker Engine kapalı ve Java/Maven/Node sistem PATH'inde yoktu. Docker Desktop daha sonra başlatıldı; Docker tabanlı derleme/çalıştırma kullanıldı. Java'nın yerel kurulumu Compose için gerekmedi.
- Spring Boot 3.5.6 backend, Flyway/PostgreSQL 16 migration ve seed, React/Vite SPA, Nginx, Compose, CI ve Türkçe dokümantasyon oluşturuldu.
- Docker Compose tüm servisleri kurdu. PostgreSQL sağlıklı; Spring Boot healthcheck geçti; frontend `http://localhost:3000` üzerinden HTTP 200 döndürüyor. API smoke testinde giriş, dashboard, liste/kategori API'leri ve uçtan uca talep atama → başlatma → onaya gönderme → onay akışı doğrulandı.
- Tarayıcı E2E testinde CSRF token'ın Axios tarafından cookie'den gelen ham değerle ezildiği saptandı. Token API yanıtından okunup yazma isteklerine açıkça ekleniyor; otomatik XSRF cookie başlığı kapatıldı. Düzeltmeden sonra iki Playwright senaryosu geçti.
- Backend Maven test/derlemesi: 19 başarılı, 1 Testcontainers migration testi Docker soketine erişemediği için atlandı. Flyway migration ayrıca gerçek Compose PostgreSQL 16 üzerinde başarıyla uygulandı.
- Frontend Vitest: 5/5 başarılı. Frontend production build başarılı. Playwright E2E: 2/2 başarılı.
- `docker compose config` başarılı. Önceki build'lerdeki frontend CSRF sorunu düzeltildikten sonra Docker frontend imajı yeniden derlenip servisler tekrar başlatıldı.

## Bilinen kapsam notları
- Ticket yorum API'si ve kategori yönetim arayüzü henüz yoktur.
- Backend testleri kritik durum/SLA iş kurallarını kapsıyor; rol matrisi, audit transaction bütünlüğü, bildirim tekilleştirme ve gerçek optimistic-lock yarışları için ek otomatik testler gerekir.
- Dashboard tamamlanma oranı görünür ilk 100 kayıttan hesaplanır; tüm zamanlı kurumsal performans ölçümü değildir.
- Frontend JavaScript bundle'ı minify sonrası yaklaşık 1.19 MB; route bazlı kod bölme önerilir.
- Testcontainers otomatik migration testi bu ortamda çalıştırılamadı. Gerçek migration başarısı uygulamanın Compose ile başlatılması üzerinden doğrulandı.

# Stajyer rehberi

## 1. Projenin amacı

FlowDesk, bir kurum içi iş talebinin oluşturulmasından atama, çalışma, yönetici onayı ve denetimine kadar geçen süreci öğretmek için hazırlanmıştır. Kişi ve talepler sentetiktir.

## 2. Mimari bileşenler

React/TypeScript arayüzü Vite veya Nginx üzerinden `/api` çağırır. Spring Boot modüler monolitinde `security`, `ticket`, `admin` ve `core` paketleri bulunur. JPA, PostgreSQL'e erişir; Flyway sürümlü SQL ile şemayı yönetir. Scheduler SLA son tarihlerini kontrol eder.

## 3. Bir HTTP isteğinin yolculuğu

Kullanıcı formu Axios ile `/api/v1/tickets` isteği oluşturur. Proxy isteği backend'e iletir. Spring Security session ve CSRF kontrolü yapar. Controller DTO Bean Validation uygular. Service rol ve durum kurallarını kontrol ederek transaction açar. Repository SQL'i PostgreSQL'e gönderir. Ticket ve audit aynı transaction'da kaydedilir. Controller güvenli DTO'yu JSON olarak döndürür; React Query listeyi yeniler.

## 4. Katman sorumlulukları

- Controller: HTTP, kimlik/rol bağlamı, DTO doğrulaması ve durum kodu.
- Service: transaction, iş kuralları, audit ve bildirim yan etkileri.
- Repository: sorgu ve kalıcılık; sorgu filtrelerini SQL tarafında çalıştırma.
- DTO: dış API sözleşmesi; entity ve parola hash'i API'ye sızdırmama.

## 5. Spring Security yetkilendirme akışı

Form login `UserDetailsService` üzerinden aktif kullanıcıyı bulur. BCrypt hash doğrulanır; roller `ROLE_*` otoritesine çevrilir. Session cookie tarayıcıda HttpOnly'dir. Değişiklik isteklerinde CSRF cookie/header çiftinin doğrulanması gerekir. Menü öğelerini gizlemek tek başına güvenlik sağlamaz; endpoint'lerde rol kontrolü ve talep sahipliği ayrıca uygulanır.

## 6. Veritabanı ilişkileri

Bir kullanıcı birden fazla role sahip olabilir (`user_roles`). Talepte zorunlu oluşturan, isteğe bağlı atanan kullanıcı ve kategori vardır. Talep audit satırları `ticket_number` ile izlenir. Bildirim kullanıcı adına bağlıdır ve sadece sahibi görebilir. `ticket_comments` ilerletme görevi için şemada hazırdır.

## 7. İş akışı

`OPEN → ASSIGNED → IN_PROGRESS → PENDING_APPROVAL → COMPLETED`. Yönetici reddi `IN_PROGRESS`'e döner. Çalışan sadece kendine atanan işi başlatıp onaya gönderebilir. Yönetici kendi atamasını onaylayamaz. Talep sahibi kendi `OPEN` işini iptal edebilir; yönetici gerekçeyle terminal olmayan kaydı iptal eder. `TicketWorkflow` geçiş kararlarının tek saf fonksiyonudur.

## 8. Transaction mantığı

Atama, öncelik ve durum değiştirme `@Transactional` service metodundadır. Değişiklik ve audit kaydı tek transaction'da olduğundan audit yazımı başarısız olursa talep değişikliği de geri alınır.

## 9. Optimistic locking

`Ticket.version`, JPA `@Version` alanıdır. İki yönetici aynı sürümü okursa ilk commit sürümü artırır; ikinci yazma optimistic lock hatası alır ve API anlaşılır 409 döndürür. Bu yaklaşım, bu iş hacminde DB satır kilitlerini uzun süre tutmadan kayıp güncellemeyi önler.

## 10. SLA

Talep oluşturulma anında P1=4, P2=24, P3=72 saatlik takvim süresi `Clock` ile hesaplanıp DB'ye yazılır. Scheduler her dakika terminal olmayan ve süresi geçmiş işleri tarar. Atanan kişiye ticket başına tek SLA bildirimi oluşturulur. Mesai/tatil takvimi bu sürümde yoktur.

## 11. Testleri çalıştırma

Java 21 ve Maven ile `cd backend; mvn test`; Node 22 ile `cd frontend; npm install; npm test`; tarayıcı senaryoları için `npm run e2e`. Compose doğrulaması için Docker Desktop Linux Engine açıkken `docker compose up --build`. PostgreSQL gerektiren entegrasyon testleri Testcontainers kullanmalıdır.

## 12. İncelenecek kritik dosyalar

`TicketWorkflow.java`, `TicketService.java`, `Ticket.java`, `TicketController.java`, `SecurityConfig.java`, `UserAccount.java`, `V1__init.sql`, `DevSeed.java`, `TicketTable.tsx`, `App.tsx`.

## 13. Teknik sorular

1. `@Version` yoksa iki yönetici güncellemesi nasıl veri kaybı üretir?
2. Neden `TicketView` entity yerine API'den döner?
3. CSRF saldırısı session cookie'li uygulamada nasıl çalışır?
4. `@Transactional` hangi işlemleri atomik kılar?
5. Neden audit tablosunda değişiklik/silme API'si yok?
6. Ticket numarası UUID parçası yerine sequence olursa neler değişir?
7. SLA'yı neden oluşturma anında saklarız?
8. Queue sorgu indeksi hangi sıralama maliyetini azaltır?
9. `PENDING_APPROVAL` geçişini kimler tetikleyebilir?
10. Bildirim sahipliği kontrolü hangi nesne erişim açığını önler?
11. Seed'in tekrarlanabilir olması hangi geliştirme işini kolaylaştırır?
12. Audit insert'i ayrı transaction'a alınırsa ne bozulabilir?

## 14. Geliştirme görevleri

1. Çalışan yorum ekleme API'sini `ticket_comments` tablosuna bağla.
2. Yönetici atama/öncelik için React formu ekle ve gerekçe zorunluluğunu göster.
3. Listeye tarih aralığı ve backend sayfalama kontrollerini ekle.
4. Dashboard'a aylık tamamlanma grafiği ve performans API'si ekle.
5. Testcontainers ile gerçek PostgreSQL migration ve optimistic-lock entegrasyon testi ekle.

## 15. Mentör kontrol listesi

- [ ] Stajyer API akışını katman katman anlatabiliyor.
- [ ] Rol kontrolü ile kayıt sahipliği arasındaki farkı açıklıyor.
- [ ] Yeni iş kuralını servis ve testte uyguluyor.
- [ ] Transaction rollback davranışını gösterebiliyor.
- [ ] Optimistic lock 409 senaryosunu yeniden üretebiliyor.
- [ ] Flyway migration'ı geri uyumlu ve indeks gerekçeli yazıyor.
- [ ] Audit/bildirim gizliliğini koruyor.
- [ ] Frontend yüklenme, hata ve boş durumlarını ele alıyor.
- [ ] Testi kaldırmadan başarısız davranışı düzeltiyor.
- [ ] Demo sırlarını üretim yapılandırmasından ayırıyor.

## Mimari alternatifler

JWT/localStorage seçilmedi; tarayıcıda uzun ömürlü bearer sır yönetimi ve token iptali ek yük getirir. Mikroservis/event broker seçilmedi; tek ürün sınırı için dağıtık transaction ve operasyon yükü gereksizdir. Entity'yi JSON'a çevirmek yerine DTO seçildi; kalıcı model değişikliğinin API'ye sızmasını önler. Optimistic lock seçildi; güncelleme çakışması olası ama kısa süreli ve nadir olduğundan pessimistic lock yerine 409 ile yeniden deneme uygundur.

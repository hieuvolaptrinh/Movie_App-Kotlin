# 📁 data — Tầng dữ liệu (Data Layer)

## Nhiệm vụ

Folder `data` là **tầng dữ liệu** trong mô hình MVVM — chịu trách nhiệm **lấy, lưu trữ và cung cấp dữ liệu** cho tầng Domain.

Tầng này biết cách:
- Gọi **API** từ internet (`remote`)
- Đọc/ghi **database** trên thiết bị (`local`)
- **Ánh xạ** JSON response thành Kotlin object (`model`)
- **Phối hợp** giữa local và remote (`repository`)

---

## Trong MVVM, `data` là tầng dưới cùng — không biết gì về UI

```
┌─────────────────────────────────────────────────────────┐
│                      UI LAYER                           │
│              Activity / Fragment / ViewModel            │
└────────────────────────┬────────────────────────────────┘
                         │ gọi UseCase
┌────────────────────────▼────────────────────────────────┐
│                    DOMAIN LAYER                         │
│            UseCase / Model / Repository (Interface)     │
└────────────────────────┬────────────────────────────────┘
                         │ implement Interface
┌────────────────────────▼────────────────────────────────┐
│                     DATA LAYER  ◄── Bạn đang ở đây      │
│  ┌─────────┐  ┌──────────┐  ┌────────┐  ┌──────────┐   │
│  │  model  │  │  remote  │  │ local  │  │repository│   │
│  │ (DTO)   │  │  (API)   │  │  (DB)  │  │  (Impl)  │   │
│  └─────────┘  └──────────┘  └────────┘  └──────────┘   │
└─────────────────────────────────────────────────────────┘
```

---

## Cấu trúc thư mục

```
data/
├── model/          ← DTO: ánh xạ JSON response từ API
│   └── MovieResponse.kt
│   └── MovieDto.kt
│
├── remote/         ← Network: định nghĩa endpoint HTTP
│   └── ApiService.kt
│   └── RetrofitClient.kt
│
├── local/          ← Database: Room/SQLite lưu offline
│   └── AppDatabase.kt
│   └── MovieDao.kt
│   └── MovieEntity.kt
│
└── repository/     ← Triển khai Repository Interface
    └── MovieRepositoryImpl.kt
```

---

## Nguyên tắc của Data Layer

| Nguyên tắc | Giải thích |
|---|---|
| **Không tham chiếu UI** | Data layer không import bất kỳ class Android UI nào |
| **Chỉ trả về Domain Model** | Repository luôn convert DTO → Domain Model trước khi trả về |
| **Ẩn nguồn dữ liệu** | ViewModel/UseCase không cần biết dữ liệu đến từ API hay cache |
| **Xử lý lỗi tại đây** | Bắt exception mạng, lỗi DB rồi wrap thành `Result` hoặc `Resource` |

---

## Luồng dữ liệu đầy đủ

```
[Internet / TMDB API]
        │ JSON
        ▼
[data/remote/ApiService]  ──→  MovieResponse (DTO)
        │
        ▼
[data/repository/MovieRepositoryImpl]
        │  convert DTO → Domain Model
        │  cache vào Room nếu cần
        ▼
[domain/model/Movie]  ──→  [domain/usecase]  ──→  [ViewModel]  ──→  [UI]
```

---

## Trạng thái hiện tại của dự án

> ⚠️ Tầng Data hiện **chưa được triển khai** — dữ liệu phim đang hardcode thẳng trong `HomeActivity.kt`.
>
> Các folder `local`, `model`, `remote`, `repository` đã được tạo sẵn — sẵn sàng để implement khi tích hợp API.

**Thứ tự implement đề xuất:**
1. Tạo `domain/model/Movie.kt` và `domain/repository/MovieRepository.kt`
2. Tạo `data/model/MovieDto.kt` và `data/remote/ApiService.kt`
3. Implement `data/repository/MovieRepositoryImpl.kt`
4. Tạo `HomeViewModel.kt` dùng UseCase
5. Refactor `HomeActivity.kt` → chỉ còn observe ViewModel

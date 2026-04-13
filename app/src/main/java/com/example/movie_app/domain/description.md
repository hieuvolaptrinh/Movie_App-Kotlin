# 📁 domain — Business Logic (Tầng nghiệp vụ)

## Nhiệm vụ

Folder `domain` là **trái tim của ứng dụng** — nơi chứa toàn bộ **logic nghiệp vụ** và các **quy tắc hoạt động của app**.

Tầng `domain`:
- **KHÔNG biết** UI hiển thị thế nào
- **KHÔNG biết** dữ liệu đến từ API hay database
- **Chỉ quan tâm**: "App này làm gì và làm theo quy tắc nào?"

Đây là tầng **độc lập nhất** — có thể copy sang app khác (iOS, Web) mà không cần thay đổi.

---

## Trong MVVM, `domain` nằm ở giữa — giao tiếp với cả Data và UI

```
          [Data Layer]            [Domain Layer]          [UI Layer]
  ┌─────────────────────┐    ┌──────────────────┐    ┌──────────────┐
  │ data/remote (API)   │───►│ domain/repository│    │              │
  │ data/local  (DB)    │    │    (Interface)   │    │  ViewModel   │
  └─────────────────────┘    │                  │◄───│              │
                             │ domain/usecase   │    │              │
                             │ domain/model     │    │              │
                             └──────────────────┘    └──────────────┘
```

---

## Thường chứa những gì?

```
domain/
├── model/          ← Các entity thuần Kotlin, không phụ thuộc gì
│   └── Movie.kt
├── repository/     ← Interface (bản hợp đồng) — được implement ở data/
│   └── MovieRepository.kt
└── usecase/        ← Mỗi usecase = 1 action cụ thể của app
    ├── GetPopularMoviesUseCase.kt
    └── GetMovieDetailUseCase.kt
```

---

## Ví dụ code — Toàn bộ tầng Domain

```kotlin
// domain/model/Movie.kt — Domain Entity (thuần Kotlin, không annotation)
data class Movie(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val rating: Double,
    val description: String = ""
)

// domain/repository/MovieRepository.kt — Interface (hợp đồng)
interface MovieRepository {
    suspend fun getPopularMovies(): Result<List<Movie>>
    suspend fun getTodayMovies(): Result<List<Movie>>
    suspend fun getMovieDetail(id: Int): Result<Movie>
}

// domain/usecase/GetPopularMoviesUseCase.kt — 1 use case = 1 nhiệm vụ
class GetPopularMoviesUseCase(
    private val repository: MovieRepository
) {
    // invoke() cho phép gọi như hàm: useCase()
    suspend operator fun invoke(): Result<List<Movie>> {
        return repository.getPopularMovies()
        // Có thể thêm logic nghiệp vụ tại đây, ví dụ:
        // .map { movies -> movies.filter { it.rating >= 7.0 } }
    }
}
```

**ViewModel sử dụng UseCase:**
```kotlin
class HomeViewModel(
    private val getPopularMovies: GetPopularMoviesUseCase
) : ViewModel() {
    fun loadMovies() = viewModelScope.launch {
        val result = getPopularMovies()  // gọi như hàm bình thường!
        // xử lý result...
    }
}
```

---

## Vì sao cần UseCase thay vì gọi Repository thẳng?

| Gọi thẳng Repository ❌ | Dùng UseCase ✅ |
|---|---|
| Logic nằm trong ViewModel → khó test | Logic riêng một chỗ → test dễ |
| Nhiều ViewModel dùng chung → duplicate code | UseCase tái sử dụng được |
| ViewModel biết quá nhiều thứ | ViewModel chỉ gọi UseCase — đơn giản |

---

## Trạng thái hiện tại của dự án

> ⚠️ Tầng Domain hiện chưa được triển khai — logic đang nằm hết trong `HomeActivity.kt`.
>
> Bước đầu tiên khi refactor sang MVVM chuẩn:
> 1. Tạo `domain/model/Movie.kt`
> 2. Tạo `domain/repository/MovieRepository.kt` (Interface)
> 3. Tạo `domain/usecase/GetPopularMoviesUseCase.kt`

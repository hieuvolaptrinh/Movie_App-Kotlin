# 📁 data/model — Data Transfer Objects (DTO)

## Nhiệm vụ

Folder `model` chứa các **data class dùng để ánh xạ dữ liệu từ API** (JSON response).  
Chúng còn được gọi là **DTO — Data Transfer Object**: đối tượng trung gian nhận dữ liệu từ server về.

Đây **KHÔNG phải** là model dùng để hiển thị lên UI — chúng chỉ phản ánh đúng cấu trúc JSON mà server trả về.

---

## Trong MVVM, `model` thuộc tầng **Data Layer**

```
JSON từ API ──► data/model (DTO) ──► Repository ──► domain/model ──► ViewModel ──► UI
                    ▲
              Bạn đang ở đây
```

---

## Thường chứa những gì?

| File | Mô tả |
|---|---|
| `MovieResponse.kt` | Ánh xạ toàn bộ response JSON từ API |
| `MovieDto.kt` | Đại diện cho 1 item phim trong list JSON |
| `GenreDto.kt` | Thể loại phim từ API |

---

## Ví dụ code — API trả về dạng JSON

**JSON từ server:**
```json
{
  "page": 1,
  "results": [
    {
      "id": 101,
      "title": "Black Widow",
      "poster_path": "/bImage.jpg",
      "vote_average": 7.8
    }
  ]
}
```

**Kotlin DTO tương ứng:**
```kotlin
// MovieResponse.kt — ánh xạ toàn bộ JSON object gốc
data class MovieResponse(
    @SerializedName("page")   val page: Int,
    @SerializedName("results") val results: List<MovieDto>
)

// MovieDto.kt — ánh xạ 1 phần tử trong mảng "results"
data class MovieDto(
    @SerializedName("id")           val id: Int,
    @SerializedName("title")        val title: String,
    @SerializedName("poster_path")  val posterPath: String,
    @SerializedName("vote_average") val voteAverage: Double
)
```

---

## Phân biệt DTO vs Domain Model

| | DTO (`data/model`) | Domain Model (`domain/model`) |
|---|---|---|
| **Mục đích** | Nhận dữ liệu từ API | Dùng trong business logic & UI |
| **Tên field** | Theo tên key trong JSON | Theo convention Kotlin (camelCase) |
| **Annotation** | `@SerializedName`, `@Json` | Không có |
| **Dependency** | Phụ thuộc vào API | Độc lập hoàn toàn |

---

## Trạng thái hiện tại của dự án

> ⚠️ Dự án hiện chưa dùng API — dữ liệu phim đang được hardcode trực tiếp trong `HomeActivity.kt`:
> ```kotlin
> TopMovieModel(R.drawable.popular_1, "Black Widow")
> ```
> Khi tích hợp API (TMDB chẳng hạn), các DTO sẽ được tạo ở đây để nhận JSON và convert sang domain model đưa lên UI.

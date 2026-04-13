# 📁 until — Các tiện ích dùng chung (Utilities)

## ⚠️ Ghi chú về tên folder

> Tên chuẩn thường dùng là **`util`** hoặc **`utils`** (viết tắt của "utilities").  
> Folder này đang được đặt là `until` — nên đổi lại thành `util` để đúng convention.

---

## Nhiệm vụ

Folder `util` chứa các **class và function tiện ích dùng chung** — những đoạn code không thuộc về bất kỳ tầng cụ thể nào nhưng được dùng ở nhiều nơi trong app.

Nguyên tắc: nếu 1 đoạn code được copy-paste từ 2 class trở lên → nên chuyển vào `util`.

---

## Trong MVVM, `util` là tầng hỗ trợ — không thuộc Data, Domain, hay UI

```
          [Data]     [Domain]     [UI/ViewModel]
             │           │              │
             └───────────┴──────────────┘
                         │
                      [util]  ← Ai cũng có thể dùng
```

---

## Thường chứa những gì?

| File | Mô tả |
|---|---|
| `Extensions.kt` | Extension functions cho String, Int, View,... |
| `Constants.kt` | Hằng số dùng trong toàn app (API key, URL,...) |
| `DateUtils.kt` | Hàm format ngày tháng |
| `NetworkUtils.kt` | Kiểm tra kết nối mạng |
| `ImageUtils.kt` | Load ảnh bằng Glide/Picasso, format URL ảnh |
| `Resource.kt` | Sealed class bọc trạng thái Loading/Success/Error |

---

## Ví dụ code thực tế — các utility hay dùng trong Movie App

```kotlin
// Constants.kt — tập trung hằng số
object Constants {
    const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    const val TMDB_API_KEY   = BuildConfig.TMDB_API_KEY
}

// Extensions.kt — extension functions tiện lợi
fun Int.dp(context: Context): Int {
    // Ví dụ đã dùng trong TopMovieAdapter.kt (khai báo thành extension)
    return (this * context.resources.displayMetrics.density).toInt()
}

fun String.formatImageUrl(): String {
    return "${Constants.BASE_IMAGE_URL}${this}"
}

fun View.show() { this.visibility = View.VISIBLE }
fun View.hide() { this.visibility = View.GONE }
fun View.invisible() { this.visibility = View.INVISIBLE }

// Resource.kt — sealed class để wrap API response state
sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}

// Dùng trong ViewModel:
// _uiState.value = Resource.Loading
// _uiState.value = Resource.Success(movies)
// _uiState.value = Resource.Error("Không có kết nối mạng")
```

---

## Extension đã có trong dự án (trong TopMovieAdapter.kt)

Dự án hiện có một extension `dp` được viết thẳng vào trong Adapter — nên chuyển vào đây:

```kotlin
// ⚠️ Hiện đang ở TopMovieAdapter.kt — nên chuyển sang util/Extensions.kt
val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
```

---

## Trạng thái hiện tại của dự án

> Folder `until` hiện chưa có file nào.  
> Khi dự án phát triển thêm (API, Database, ViewModel), hãy đưa các đoạn code tái sử dụng vào đây.
>
> **Gợi ý tạo ngay:**
> - `Constants.kt` — lưu key, URL
> - `Extensions.kt` — chuyển `Int.dp` từ Adapter vào đây
> - `Resource.kt` — để ViewModel dễ handle trạng thái Loading/Success/Error
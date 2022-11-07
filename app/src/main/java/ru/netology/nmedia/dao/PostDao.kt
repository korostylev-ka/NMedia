package ru.netology.nmedia.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity

//dao - значит через него доступ к базе данных
@Dao
interface PostDao {
    //функция getAll kotlin соответствует следующему SQL запросу, результат попадет в функцию котлин
    //извлекает все записи PostEntity, сортируя по убыванию
    @Query("SELECT * FROM PostEntity ORDER BY id DESC")
    fun getAll(): LiveData<List<PostEntity>>

    //Добавление новой записи в таблицу
    @Insert
    fun insert(post: PostEntity)

    //обновляет в таблице postEntity значение столбца content, где id = id аргумента, : - сслыка на параметры функции
    @Query("UPDATE PostEntity SET content = :content WHERE id = :id")
    fun updateContentById(id: Long, content: String?)

    fun save(post: PostEntity) =
    if (post.id == 0L) insert(post) else updateContentById(post.id, post.content)

    @Query("""
        UPDATE PostEntity SET
        likes = likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
        likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
        WHERE id = :id
        """)
    fun likeById(id: Long)

    @Query("DELETE FROM PostEntity WHERE id = :id")
    fun removeById(id: Long)

    @Query("""
           UPDATE PostEntity SET
               shares = shares + 1 WHERE id = :id
        """)
    fun shareById(id: Long)
}
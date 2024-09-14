package com.example.demo.blog.repository

import com.example.demo.blog.entity.Wordcount
import org.springframework.data.repository.CrudRepository

interface WordRepository : CrudRepository<Wordcount, String>{
    fun findTop10ByOrderByCntDesc(): List<Wordcount>
}

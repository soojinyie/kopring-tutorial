package com.example.demo.blog.service

import com.example.demo.blog.dto.BlogDto
import com.example.demo.core.exception.InvalidInputException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class BlogService {
    @Value("\${REST_API_KEY}")
    lateinit var restApiKey: String

    fun searchKakao(blogDto: BlogDto): String? {
        val msgLst = mutableListOf<ExceptionMsg>()

        if (blogDto.query.trim().isEmpty()){
            msgLst.add(ExceptionMsg.EMPTY_QUERY)
        }

        if (blogDto.sort.trim() !in arrayOf("accuracy", "recency")){
            msgLst.add(ExceptionMsg.NOT_IN_SORT)
        }

        when {
            blogDto.page < 1 -> msgLst.add(ExceptionMsg.LESS_THAN_MIN)
            blogDto.page > 50 -> msgLst.add(ExceptionMsg.MORE_THAN_MAX)
        }

        if (msgLst.isNotEmpty()){
            val message = msgLst.joinToString {it.msg}
            throw  InvalidInputException(message)
        }

        val webClient = WebClient
            .builder()
            .baseUrl("https://dapi.kakao.com")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()

        val response = webClient
            .get()
            .uri { it.path("/v2/search/blog")
                .queryParam("query", blogDto.query)
                .queryParam("sort", blogDto.sort)
                .queryParam("page", blogDto.page)
                .queryParam("size", blogDto.size)
                .build() }
            .header("Authorization", "KakaoAK ${restApiKey}")
            .retrieve()
            .bodyToMono<String>()

        val result = response.block()

        return result
    }
}

private enum class ExceptionMsg(val msg: String){
    EMPTY_QUERY("query parameter required"),
    NOT_IN_SORT("sort parameter should be accuracy or recency"),
    LESS_THAN_MIN("page parameter should not be less than 1"),
    MORE_THAN_MAX("page parameter should not exceed 50")

}
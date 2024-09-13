package com.example.demo.blog.controller

import com.example.demo.blog.dto.BlogDto
import com.example.demo.blog.service.BlogService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/blog")
@RestController
class BlogController (
//    서비스를 호출할 것이기 때문에 클래스 생성자에 service 를 넣어줌
    val blogService: BlogService
){
//    사용자가 호출을 했을 때 그 호출을 받아줄 함수
//    /api/blog 로 get request 시, 아래의 함수로 연결되며, 전달한 값들은 blogDto 에 담기고, 그게 그대로 blogService 에 전달됨.
//    그 호출은 BlogService 의 searchKakao 함수가 받으면서 println 을 한 번 하고 "searchKakao" 문자열을 리턴함
//    그 값을 search 함수에서 result 에 담아서 리턴하면 클라이언트 측에 전달됨
    @GetMapping("")
    fun search(@RequestBody @Valid blogDto: BlogDto): String? {
        val result = blogService.searchKakao(blogDto)
        return result
    }
}
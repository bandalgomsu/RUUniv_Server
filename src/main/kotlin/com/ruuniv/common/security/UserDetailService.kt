package com.ruuniv.common.security

import com.ruuniv.common.exception.BusinessException
import com.ruuniv.common.security.exception.AuthErrorCode
import com.ruuniv.infrastricture.database.users.UserReactiveRepository
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class UserDetailService(
    private val userRepository: UserReactiveRepository,
) : ReactiveUserDetailsService {

    override fun findByUsername(username: String): Mono<UserDetails> {
        return userRepository.findByEmail(email = username)
            .switchIfEmpty { Mono.error(BusinessException(AuthErrorCode.NOT_EXIST_USER)) }
            .flatMap { Mono.just(CustomUserDetails(it)) }
    }
}
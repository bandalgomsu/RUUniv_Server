package com.ruuniv.app.auth.services

import JwtToken
import com.ruuniv.app.auth.dto.AuthRequest
import com.ruuniv.app.auth.dto.AuthResponse
import com.ruuniv.common.exception.BusinessException
import com.ruuniv.common.security.exception.AuthErrorCode
import com.ruuniv.common.security.jwt.JwtService
import com.ruuniv.common.util.MailValidator
import com.ruuniv.infrastricture.dao.repository.users.UserCoroutineRepository
import com.ruuniv.infrastricture.dao.repository.users.entities.Role
import com.ruuniv.infrastricture.dao.repository.users.entities.UserEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val jwtService: JwtService,
    private val userRepository: UserCoroutineRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    @Transactional
    suspend fun signUp(request: AuthRequest.SignUpRequest): AuthResponse.TokenResponse = coroutineScope {
        MailValidator.validateEmail(email = request.email)
        async { checkIsDuplicatedEmail(email = request.email) }.await()

        val user: UserEntity =
            UserEntity(
                email = request.email,
                password = passwordEncoder.encode(request.password),
                role = Role.USER
            )

        val savedUser: UserEntity = userRepository.save(user)

        createToken(savedUser.email)
    }


    private suspend fun checkIsDuplicatedEmail(email: String) = coroutineScope {
        if (userRepository.findByEmail(email) != null) {
            throw BusinessException(AuthErrorCode.DUPLICATE_EMAIL)
        }
    }

    suspend fun login(request: AuthRequest.LoginRequest): AuthResponse.TokenResponse = coroutineScope {
        val user: UserEntity =
            userRepository.findByEmail(request.email) ?: throw BusinessException(AuthErrorCode.NOT_EXIST_USER)

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw BusinessException(AuthErrorCode.NOT_EXIST_USER)
        }

        createToken(user.email)
    }

    suspend fun refresh(request: AuthRequest.RefreshRequest): AuthResponse.TokenResponse = coroutineScope {
        val token: JwtToken = JwtToken(request.refreshToken)

        jwtService.validate(token).awaitSingleOrNull()

        createToken(jwtService.getEmail(token).awaitSingle())
    }

    private suspend fun createToken(email: String): AuthResponse.TokenResponse = coroutineScope {
        val accessToken: JwtToken = jwtService.generateAccessToken(email).awaitSingle()
        val refreshToken: JwtToken = jwtService.generateRefreshToken(email).awaitSingle()

        AuthResponse.TokenResponse(
            accessToken = accessToken.value,
            refreshToken = refreshToken.value
        )
    }
}
package me.jaeyeop.tickethub.support

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder

@ExtendWith(RestDocumentationExtension::class)
abstract class RestDocsSupport {

    protected lateinit var mockMvc: MockMvc

    private val objectMapper: ObjectMapper = ObjectMapper()

    @BeforeEach
    fun setup(provider: RestDocumentationContextProvider) {
        mockMvc = MockMvcBuilders
            .standaloneSetup(controller())
            .apply<StandaloneMockMvcBuilder>(
                documentationConfiguration(provider)
                    .operationPreprocessors()
                    .withRequestDefaults(Preprocessors.prettyPrint())
                    .withResponseDefaults(Preprocessors.prettyPrint())
            ).alwaysDo<StandaloneMockMvcBuilder>(document("{class-name}/{method-name}"))
            .alwaysDo<StandaloneMockMvcBuilder>(print())
            .build()
    }

    protected fun <T> convert(obj: T): String {
        return objectMapper.writeValueAsString(obj)
    }

    protected abstract fun controller(): Any

}

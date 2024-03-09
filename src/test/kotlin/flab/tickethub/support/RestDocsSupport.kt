package me.jaeyeop.tickethub.support

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.restassured.module.mockmvc.RestAssuredMockMvc
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.snippet.Snippet
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder
import java.nio.charset.Charset

@ExtendWith(RestDocumentationExtension::class)
abstract class RestDocsSupport {

    private lateinit var mockMvc: MockMvcRequestSpecification

    private val resultHandler: RestDocumentationResultHandler =
        document("{class-name}/{method-name}")

    private val objectMapper: ObjectMapper = jacksonObjectMapper()

    @BeforeEach
    fun setup(provider: RestDocumentationContextProvider) {
        mockMvc = RestAssuredMockMvc.given().mockMvc(
            MockMvcBuilders
                .standaloneSetup(controller())
                .defaultResponseCharacterEncoding<StandaloneMockMvcBuilder>(Charset.defaultCharset())
                .apply<StandaloneMockMvcBuilder>(
                    documentationConfiguration(provider)
                        .operationPreprocessors()
                        .withRequestDefaults(Preprocessors.prettyPrint())
                        .withResponseDefaults(Preprocessors.prettyPrint())
                )
                .setMessageConverters(MappingJackson2HttpMessageConverter(objectMapper))
                .alwaysDo<StandaloneMockMvcBuilder>(print())
                .alwaysDo<StandaloneMockMvcBuilder>(resultHandler)
                .build()
        )
    }

    protected fun given(): MockMvcRequestSpecification {
        return mockMvc
    }

    protected fun document(vararg snippets: Snippet): RestDocumentationResultHandler? {
        return resultHandler.document(*snippets)
    }

    protected fun <T> convert(obj: T): String {
        return objectMapper.writeValueAsString(obj)
    }

    protected abstract fun controller(): Any

}

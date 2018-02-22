package test.trendingrepos.common.api.converters

import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import com.google.gson.annotations.SerializedName


/**
 * Retrofit does not respect enums in query params
 * https://github.com/square/retrofit/issues/711
 *
 * Created on 22/02/2018
 * @author sdelaysam
 */

class RetrofitEnumConverter : Converter.Factory() {

    override fun stringConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<*, String>? {
        if (type is Class<*> && type.isEnum) {
            return Converter<Enum<*>, String> { value -> getSerializedNameValue(value) }
        }
        return null
    }

    private fun getSerializedNameValue(e: Enum<*>): String? {
        var value: String? = null
        try {
            value = e.javaClass.getField(e.name).getAnnotation(SerializedName::class.java).value
        } catch (exception: NoSuchFieldException) {
            exception.printStackTrace()
        }

        return value
    }
}
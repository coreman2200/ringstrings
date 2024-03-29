// Code generated by Wire protocol buffer compiler, do not edit.
// Source: NumerologySettings in ringstrings.proto
package com.coreman2200.ringstrings.`data`.protos

import android.os.Parcelable
import com.squareup.wire.AndroidMessage
import com.squareup.wire.EnumAdapter
import com.squareup.wire.FieldEncoding
import com.squareup.wire.ProtoAdapter
import com.squareup.wire.ProtoReader
import com.squareup.wire.ProtoWriter
import com.squareup.wire.ReverseProtoWriter
import com.squareup.wire.Syntax.PROTO_3
import com.squareup.wire.WireEnum
import com.squareup.wire.WireField
import okio.ByteString
import kotlin.Any
import kotlin.AssertionError
import kotlin.Boolean
import kotlin.Deprecated
import kotlin.DeprecationLevel
import kotlin.Int
import kotlin.Long
import kotlin.Nothing
import kotlin.String
import kotlin.jvm.JvmField
import kotlin.jvm.JvmStatic

/**
 * Settings necessary for Numerological chart building
 */
public class NumerologySettings(
    @field:WireField(  
        tag = 1,  
        adapter =  
        "NumerologySettings${'$'}NumberSystemType#ADAPTER",
        label = WireField.Label.OMIT_IDENTITY,  
        jsonName = "numberSystem"  
    )  
    public val number_system: NumberSystemType = NumberSystemType.CHALDEAN,
    unknownFields: ByteString = ByteString.EMPTY
) : AndroidMessage<NumerologySettings, Nothing>(ADAPTER, unknownFields) {
    @Deprecated(
        message = "Shouldn't be used in Kotlin",
        level = DeprecationLevel.HIDDEN
    )
    public override fun newBuilder(): Nothing = throw
    AssertionError("Builders are deprecated and only available in a javaInterop build; see https://square.github.io/wire/wire_compiler/#kotlin")

    public override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is NumerologySettings) return false
        if (unknownFields != other.unknownFields) return false
        if (number_system != other.number_system) return false
        return true
    }

    public override fun hashCode(): Int {
        var result = super.hashCode
        if (result == 0) {
            result = unknownFields.hashCode()
            result = result * 37 + number_system.hashCode()
            super.hashCode = result
        }
        return result
    }

    public override fun toString(): String {
        val result = mutableListOf<String>()
        result += """number_system=$number_system"""
        return result.joinToString(prefix = "NumerologySettings{", separator = ", ", postfix = "}")
    }

    public fun copy(
        number_system: NumberSystemType = this.number_system,
        unknownFields: ByteString =      
            this.unknownFields
    ): NumerologySettings = NumerologySettings(number_system, unknownFields)

    public companion object {
        @JvmField
        public val ADAPTER: ProtoAdapter<NumerologySettings> = object :
            ProtoAdapter<NumerologySettings>(
                FieldEncoding.LENGTH_DELIMITED,
                NumerologySettings::class,
                "type.googleapis.com/NumerologySettings",
                PROTO_3,
                null,
                "ringstrings.proto"
            ) {
            public override fun encodedSize(`value`: NumerologySettings): Int {
                var size = value.unknownFields.size
                if (value.number_system != NumberSystemType.CHALDEAN) size +=
                    NumberSystemType.ADAPTER.encodedSizeWithTag(1, value.number_system)
                return size
            }

            public override fun encode(writer: ProtoWriter, `value`: NumerologySettings) {
                if (value.number_system != NumberSystemType.CHALDEAN)
                    NumberSystemType.ADAPTER.encodeWithTag(writer, 1, value.number_system)
                writer.writeBytes(value.unknownFields)
            }

            public override fun encode(writer: ReverseProtoWriter, `value`: NumerologySettings) {
                writer.writeBytes(value.unknownFields)
                if (value.number_system != NumberSystemType.CHALDEAN)
                    NumberSystemType.ADAPTER.encodeWithTag(writer, 1, value.number_system)
            }

            public override fun decode(reader: ProtoReader): NumerologySettings {
                var number_system: NumberSystemType = NumberSystemType.CHALDEAN
                val unknownFields = reader.forEachTag { tag ->
                    when (tag) {
                        1 -> try {
                            number_system = NumberSystemType.ADAPTER.decode(reader)
                        } catch (e: ProtoAdapter.EnumConstantNotFoundException) {
                            reader.addUnknownField(tag, FieldEncoding.VARINT, e.value.toLong())
                        }
                        else -> reader.readUnknownField(tag)
                    }
                }
                return NumerologySettings(
                    number_system = number_system,
                    unknownFields = unknownFields
                )
            }

            public override fun redact(`value`: NumerologySettings): NumerologySettings = value.copy(
                unknownFields = ByteString.EMPTY
            )
        }

        @JvmField
        public val CREATOR: Parcelable.Creator<NumerologySettings> = AndroidMessage.newCreator(ADAPTER)

        private const val serialVersionUID: Long = 0L
    }

    /**
     * Currently supports two distinct number systems
     */
    public enum class NumberSystemType(
        public override val `value`: Int
    ) : WireEnum {
        CHALDEAN(0),
        PYTHAGOREAN(1),
        ;

        public companion object {
            @JvmField
            public val ADAPTER: ProtoAdapter<NumberSystemType> = object : EnumAdapter<NumberSystemType>(
                NumberSystemType::class,
                PROTO_3,
                NumberSystemType.CHALDEAN
            ) {
                public override fun fromValue(`value`: Int): NumberSystemType? =
                    NumberSystemType.fromValue(value)
            }

            @JvmStatic
            public fun fromValue(`value`: Int): NumberSystemType? = when (value) {
                0 -> CHALDEAN
                1 -> PYTHAGOREAN
                else -> null
            }
        }
    }
}

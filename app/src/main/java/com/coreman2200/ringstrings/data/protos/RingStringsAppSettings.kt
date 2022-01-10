// Code generated by Wire protocol buffer compiler, do not edit.
// Source: com.coreman2200.ringstrings.data.protos.RingStringsAppSettings in ringstrings.proto
package com.coreman2200.ringstrings.`data`.protos

import android.os.Parcelable
import com.squareup.wire.AndroidMessage
import com.squareup.wire.FieldEncoding
import com.squareup.wire.ProtoAdapter
import com.squareup.wire.ProtoReader
import com.squareup.wire.ProtoWriter
import com.squareup.wire.ReverseProtoWriter
import com.squareup.wire.Syntax
import com.squareup.wire.Syntax.PROTO_3
import com.squareup.wire.WireField
import kotlin.Any
import kotlin.AssertionError
import kotlin.Boolean
import kotlin.Deprecated
import kotlin.DeprecationLevel
import kotlin.Int
import kotlin.Long
import kotlin.Nothing
import kotlin.String
import kotlin.Unit
import kotlin.jvm.JvmField
import okio.ByteString

public class RingStringsAppSettings(
  @field:WireField(
    tag = 1,
    adapter =
        "com.coreman2200.ringstrings.data.protos.RingStringsAppSettings${'$'}GeneralSettings#ADAPTER",
    label = WireField.Label.OMIT_IDENTITY
  )
  public val general: GeneralSettings? = null,
  @field:WireField(
    tag = 2,
    adapter = "com.coreman2200.ringstrings.data.protos.AstrologySettings#ADAPTER",
    label = WireField.Label.OMIT_IDENTITY
  )
  public val astro: AstrologySettings? = null,
  @field:WireField(
    tag = 3,
    adapter = "com.coreman2200.ringstrings.data.protos.NumerologySettings#ADAPTER",
    label = WireField.Label.OMIT_IDENTITY
  )
  public val num: NumerologySettings? = null,
  @field:WireField(
    tag = 4,
    adapter = "com.squareup.wire.ProtoAdapter#INT64",
    label = WireField.Label.OMIT_IDENTITY,
    jsonName = "lastUpdate"
  )
  public val last_update: Long = 0L,
  unknownFields: ByteString = ByteString.EMPTY
) : AndroidMessage<RingStringsAppSettings, Nothing>(ADAPTER, unknownFields) {
  @Deprecated(
    message = "Shouldn't be used in Kotlin",
    level = DeprecationLevel.HIDDEN
  )
  public override fun newBuilder(): Nothing = throw
      AssertionError("Builders are deprecated and only available in a javaInterop build; see https://square.github.io/wire/wire_compiler/#kotlin")

  public override fun equals(other: Any?): Boolean {
    if (other === this) return true
    if (other !is RingStringsAppSettings) return false
    if (unknownFields != other.unknownFields) return false
    if (general != other.general) return false
    if (astro != other.astro) return false
    if (num != other.num) return false
    if (last_update != other.last_update) return false
    return true
  }

  public override fun hashCode(): Int {
    var result = super.hashCode
    if (result == 0) {
      result = unknownFields.hashCode()
      result = result * 37 + (general?.hashCode() ?: 0)
      result = result * 37 + (astro?.hashCode() ?: 0)
      result = result * 37 + (num?.hashCode() ?: 0)
      result = result * 37 + last_update.hashCode()
      super.hashCode = result
    }
    return result
  }

  public override fun toString(): String {
    val result = mutableListOf<String>()
    if (general != null) result += """general=$general"""
    if (astro != null) result += """astro=$astro"""
    if (num != null) result += """num=$num"""
    result += """last_update=$last_update"""
    return result.joinToString(prefix = "RingStringsAppSettings{", separator = ", ", postfix = "}")
  }

  public fun copy(
    general: GeneralSettings? = this.general,
    astro: AstrologySettings? = this.astro,
    num: NumerologySettings? = this.num,
    last_update: Long = this.last_update,
    unknownFields: ByteString = this.unknownFields
  ): RingStringsAppSettings = RingStringsAppSettings(general, astro, num, last_update,
      unknownFields)

  public companion object {
    @JvmField
    public val ADAPTER: ProtoAdapter<RingStringsAppSettings> = object :
        ProtoAdapter<RingStringsAppSettings>(
      FieldEncoding.LENGTH_DELIMITED, 
      RingStringsAppSettings::class, 
      "type.googleapis.com/com.coreman2200.ringstrings.data.protos.RingStringsAppSettings", 
      PROTO_3, 
      null, 
      "ringstrings.proto"
    ) {
      public override fun encodedSize(`value`: RingStringsAppSettings): Int {
        var size = value.unknownFields.size
        if (value.general != null) size += GeneralSettings.ADAPTER.encodedSizeWithTag(1,
            value.general)
        if (value.astro != null) size += AstrologySettings.ADAPTER.encodedSizeWithTag(2,
            value.astro)
        if (value.num != null) size += NumerologySettings.ADAPTER.encodedSizeWithTag(3, value.num)
        if (value.last_update != 0L) size += ProtoAdapter.INT64.encodedSizeWithTag(4,
            value.last_update)
        return size
      }

      public override fun encode(writer: ProtoWriter, `value`: RingStringsAppSettings): Unit {
        if (value.general != null) GeneralSettings.ADAPTER.encodeWithTag(writer, 1, value.general)
        if (value.astro != null) AstrologySettings.ADAPTER.encodeWithTag(writer, 2, value.astro)
        if (value.num != null) NumerologySettings.ADAPTER.encodeWithTag(writer, 3, value.num)
        if (value.last_update != 0L) ProtoAdapter.INT64.encodeWithTag(writer, 4, value.last_update)
        writer.writeBytes(value.unknownFields)
      }

      public override fun encode(writer: ReverseProtoWriter, `value`: RingStringsAppSettings):
          Unit {
        writer.writeBytes(value.unknownFields)
        if (value.last_update != 0L) ProtoAdapter.INT64.encodeWithTag(writer, 4, value.last_update)
        if (value.num != null) NumerologySettings.ADAPTER.encodeWithTag(writer, 3, value.num)
        if (value.astro != null) AstrologySettings.ADAPTER.encodeWithTag(writer, 2, value.astro)
        if (value.general != null) GeneralSettings.ADAPTER.encodeWithTag(writer, 1, value.general)
      }

      public override fun decode(reader: ProtoReader): RingStringsAppSettings {
        var general: GeneralSettings? = null
        var astro: AstrologySettings? = null
        var num: NumerologySettings? = null
        var last_update: Long = 0L
        val unknownFields = reader.forEachTag { tag ->
          when (tag) {
            1 -> general = GeneralSettings.ADAPTER.decode(reader)
            2 -> astro = AstrologySettings.ADAPTER.decode(reader)
            3 -> num = NumerologySettings.ADAPTER.decode(reader)
            4 -> last_update = ProtoAdapter.INT64.decode(reader)
            else -> reader.readUnknownField(tag)
          }
        }
        return RingStringsAppSettings(
          general = general,
          astro = astro,
          num = num,
          last_update = last_update,
          unknownFields = unknownFields
        )
      }

      public override fun redact(`value`: RingStringsAppSettings): RingStringsAppSettings =
          value.copy(
        general = value.general?.let(GeneralSettings.ADAPTER::redact),
        astro = value.astro?.let(AstrologySettings.ADAPTER::redact),
        num = value.num?.let(NumerologySettings.ADAPTER::redact),
        unknownFields = ByteString.EMPTY
      )
    }

    @JvmField
    public val CREATOR: Parcelable.Creator<RingStringsAppSettings> =
        AndroidMessage.newCreator(ADAPTER)

    private const val serialVersionUID: Long = 0L
  }

  /**
   * General app settings TODO
   */
  public class GeneralSettings(
    unknownFields: ByteString = ByteString.EMPTY
  ) : AndroidMessage<GeneralSettings, Nothing>(ADAPTER, unknownFields) {
    @Deprecated(
      message = "Shouldn't be used in Kotlin",
      level = DeprecationLevel.HIDDEN
    )
    public override fun newBuilder(): Nothing = throw
        AssertionError("Builders are deprecated and only available in a javaInterop build; see https://square.github.io/wire/wire_compiler/#kotlin")

    public override fun equals(other: Any?): Boolean {
      if (other === this) return true
      if (other !is GeneralSettings) return false
      if (unknownFields != other.unknownFields) return false
      return true
    }

    public override fun hashCode(): Int = unknownFields.hashCode()

    public override fun toString(): String = "GeneralSettings{}"

    public fun copy(unknownFields: ByteString = this.unknownFields): GeneralSettings =
        GeneralSettings(unknownFields)

    public companion object {
      @JvmField
      public val ADAPTER: ProtoAdapter<GeneralSettings> = object : ProtoAdapter<GeneralSettings>(
        FieldEncoding.LENGTH_DELIMITED, 
        GeneralSettings::class, 
        "type.googleapis.com/com.coreman2200.ringstrings.data.protos.RingStringsAppSettings.GeneralSettings",
            
        PROTO_3, 
        null, 
        "ringstrings.proto"
      ) {
        public override fun encodedSize(`value`: GeneralSettings): Int {
          var size = value.unknownFields.size
          return size
        }

        public override fun encode(writer: ProtoWriter, `value`: GeneralSettings): Unit {
          writer.writeBytes(value.unknownFields)
        }

        public override fun encode(writer: ReverseProtoWriter, `value`: GeneralSettings): Unit {
          writer.writeBytes(value.unknownFields)
        }

        public override fun decode(reader: ProtoReader): GeneralSettings {
          val unknownFields = reader.forEachTag(reader::readUnknownField)
          return GeneralSettings(
            unknownFields = unknownFields
          )
        }

        public override fun redact(`value`: GeneralSettings): GeneralSettings = value.copy(
          unknownFields = ByteString.EMPTY
        )
      }

      @JvmField
      public val CREATOR: Parcelable.Creator<GeneralSettings> = AndroidMessage.newCreator(ADAPTER)

      private const val serialVersionUID: Long = 0L
    }
  }
}

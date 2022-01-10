// Code generated by Wire protocol buffer compiler, do not edit.
// Source: com.coreman2200.ringstrings.data.protos.LocalProfileDataBundle in ringstrings.proto
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
import com.squareup.wire.`internal`.immutableCopyOf
import com.squareup.wire.`internal`.sanitize
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
import kotlin.collections.List
import kotlin.jvm.JvmField
import okio.ByteString

/**
 * Profile Data Bundle describing anticipated values to build symbol charts.
 */
public class LocalProfileDataBundle(
  /**
   * Identifier for profile
   */
  @field:WireField(
    tag = 1,
    adapter = "com.squareup.wire.ProtoAdapter#INT32",
    label = WireField.Label.OMIT_IDENTITY,
    jsonName = "profileId"
  )
  public val profile_id: Int = 0,
  /**
   * Name for display purposes..
   */
  @field:WireField(
    tag = 2,
    adapter = "com.coreman2200.ringstrings.data.protos.LocalProfileDataBundle${'$'}Name#ADAPTER",
    label = WireField.Label.OMIT_IDENTITY,
    jsonName = "displayName"
  )
  public val display_name: Name? = null,
  /**
   * Name for chart making..
   */
  @field:WireField(
    tag = 3,
    adapter = "com.coreman2200.ringstrings.data.protos.LocalProfileDataBundle${'$'}Name#ADAPTER",
    label = WireField.Label.OMIT_IDENTITY,
    jsonName = "fullName"
  )
  public val full_name: Name? = null,
  /**
   * Geographical/temporal placement for birth chart(s) in profile
   */
  @field:WireField(
    tag = 4,
    adapter = "com.coreman2200.ringstrings.data.protos.Placement#ADAPTER",
    label = WireField.Label.OMIT_IDENTITY,
    jsonName = "birthPlacement"
  )
  public val birth_placement: Placement? = null,
  /**
   * Geographical/temporal placement for current chart(s) in profile
   */
  @field:WireField(
    tag = 5,
    adapter = "com.coreman2200.ringstrings.data.protos.Placement#ADAPTER",
    label = WireField.Label.OMIT_IDENTITY,
    jsonName = "recentPlacement"
  )
  public val recent_placement: Placement? = null,
  unknownFields: ByteString = ByteString.EMPTY
) : AndroidMessage<LocalProfileDataBundle, Nothing>(ADAPTER, unknownFields) {
  @Deprecated(
    message = "Shouldn't be used in Kotlin",
    level = DeprecationLevel.HIDDEN
  )
  public override fun newBuilder(): Nothing = throw
      AssertionError("Builders are deprecated and only available in a javaInterop build; see https://square.github.io/wire/wire_compiler/#kotlin")

  public override fun equals(other: Any?): Boolean {
    if (other === this) return true
    if (other !is LocalProfileDataBundle) return false
    if (unknownFields != other.unknownFields) return false
    if (profile_id != other.profile_id) return false
    if (display_name != other.display_name) return false
    if (full_name != other.full_name) return false
    if (birth_placement != other.birth_placement) return false
    if (recent_placement != other.recent_placement) return false
    return true
  }

  public override fun hashCode(): Int {
    var result = super.hashCode
    if (result == 0) {
      result = unknownFields.hashCode()
      result = result * 37 + profile_id.hashCode()
      result = result * 37 + (display_name?.hashCode() ?: 0)
      result = result * 37 + (full_name?.hashCode() ?: 0)
      result = result * 37 + (birth_placement?.hashCode() ?: 0)
      result = result * 37 + (recent_placement?.hashCode() ?: 0)
      super.hashCode = result
    }
    return result
  }

  public override fun toString(): String {
    val result = mutableListOf<String>()
    result += """profile_id=$profile_id"""
    if (display_name != null) result += """display_name=$display_name"""
    if (full_name != null) result += """full_name=$full_name"""
    if (birth_placement != null) result += """birth_placement=$birth_placement"""
    if (recent_placement != null) result += """recent_placement=$recent_placement"""
    return result.joinToString(prefix = "LocalProfileDataBundle{", separator = ", ", postfix = "}")
  }

  public fun copy(
    profile_id: Int = this.profile_id,
    display_name: Name? = this.display_name,
    full_name: Name? = this.full_name,
    birth_placement: Placement? = this.birth_placement,
    recent_placement: Placement? = this.recent_placement,
    unknownFields: ByteString = this.unknownFields
  ): LocalProfileDataBundle = LocalProfileDataBundle(profile_id, display_name, full_name,
      birth_placement, recent_placement, unknownFields)

  public companion object {
    @JvmField
    public val ADAPTER: ProtoAdapter<LocalProfileDataBundle> = object :
        ProtoAdapter<LocalProfileDataBundle>(
      FieldEncoding.LENGTH_DELIMITED, 
      LocalProfileDataBundle::class, 
      "type.googleapis.com/com.coreman2200.ringstrings.data.protos.LocalProfileDataBundle", 
      PROTO_3, 
      null, 
      "ringstrings.proto"
    ) {
      public override fun encodedSize(`value`: LocalProfileDataBundle): Int {
        var size = value.unknownFields.size
        if (value.profile_id != 0) size += ProtoAdapter.INT32.encodedSizeWithTag(1,
            value.profile_id)
        if (value.display_name != null) size += Name.ADAPTER.encodedSizeWithTag(2,
            value.display_name)
        if (value.full_name != null) size += Name.ADAPTER.encodedSizeWithTag(3, value.full_name)
        if (value.birth_placement != null) size += Placement.ADAPTER.encodedSizeWithTag(4,
            value.birth_placement)
        if (value.recent_placement != null) size += Placement.ADAPTER.encodedSizeWithTag(5,
            value.recent_placement)
        return size
      }

      public override fun encode(writer: ProtoWriter, `value`: LocalProfileDataBundle): Unit {
        if (value.profile_id != 0) ProtoAdapter.INT32.encodeWithTag(writer, 1, value.profile_id)
        if (value.display_name != null) Name.ADAPTER.encodeWithTag(writer, 2, value.display_name)
        if (value.full_name != null) Name.ADAPTER.encodeWithTag(writer, 3, value.full_name)
        if (value.birth_placement != null) Placement.ADAPTER.encodeWithTag(writer, 4,
            value.birth_placement)
        if (value.recent_placement != null) Placement.ADAPTER.encodeWithTag(writer, 5,
            value.recent_placement)
        writer.writeBytes(value.unknownFields)
      }

      public override fun encode(writer: ReverseProtoWriter, `value`: LocalProfileDataBundle):
          Unit {
        writer.writeBytes(value.unknownFields)
        if (value.recent_placement != null) Placement.ADAPTER.encodeWithTag(writer, 5,
            value.recent_placement)
        if (value.birth_placement != null) Placement.ADAPTER.encodeWithTag(writer, 4,
            value.birth_placement)
        if (value.full_name != null) Name.ADAPTER.encodeWithTag(writer, 3, value.full_name)
        if (value.display_name != null) Name.ADAPTER.encodeWithTag(writer, 2, value.display_name)
        if (value.profile_id != 0) ProtoAdapter.INT32.encodeWithTag(writer, 1, value.profile_id)
      }

      public override fun decode(reader: ProtoReader): LocalProfileDataBundle {
        var profile_id: Int = 0
        var display_name: Name? = null
        var full_name: Name? = null
        var birth_placement: Placement? = null
        var recent_placement: Placement? = null
        val unknownFields = reader.forEachTag { tag ->
          when (tag) {
            1 -> profile_id = ProtoAdapter.INT32.decode(reader)
            2 -> display_name = Name.ADAPTER.decode(reader)
            3 -> full_name = Name.ADAPTER.decode(reader)
            4 -> birth_placement = Placement.ADAPTER.decode(reader)
            5 -> recent_placement = Placement.ADAPTER.decode(reader)
            else -> reader.readUnknownField(tag)
          }
        }
        return LocalProfileDataBundle(
          profile_id = profile_id,
          display_name = display_name,
          full_name = full_name,
          birth_placement = birth_placement,
          recent_placement = recent_placement,
          unknownFields = unknownFields
        )
      }

      public override fun redact(`value`: LocalProfileDataBundle): LocalProfileDataBundle =
          value.copy(
        display_name = value.display_name?.let(Name.ADAPTER::redact),
        full_name = value.full_name?.let(Name.ADAPTER::redact),
        birth_placement = value.birth_placement?.let(Placement.ADAPTER::redact),
        recent_placement = value.recent_placement?.let(Placement.ADAPTER::redact),
        unknownFields = ByteString.EMPTY
      )
    }

    @JvmField
    public val CREATOR: Parcelable.Creator<LocalProfileDataBundle> =
        AndroidMessage.newCreator(ADAPTER)

    private const val serialVersionUID: Long = 0L
  }

  /**
   * Full name for profile broken into segments
   */
  public class Name(
    segments: List<String> = emptyList(),
    unknownFields: ByteString = ByteString.EMPTY
  ) : AndroidMessage<Name, Nothing>(ADAPTER, unknownFields) {
    @field:WireField(
      tag = 1,
      adapter = "com.squareup.wire.ProtoAdapter#STRING",
      label = WireField.Label.REPEATED
    )
    public val segments: List<String> = immutableCopyOf("segments", segments)

    @Deprecated(
      message = "Shouldn't be used in Kotlin",
      level = DeprecationLevel.HIDDEN
    )
    public override fun newBuilder(): Nothing = throw
        AssertionError("Builders are deprecated and only available in a javaInterop build; see https://square.github.io/wire/wire_compiler/#kotlin")

    public override fun equals(other: Any?): Boolean {
      if (other === this) return true
      if (other !is Name) return false
      if (unknownFields != other.unknownFields) return false
      if (segments != other.segments) return false
      return true
    }

    public override fun hashCode(): Int {
      var result = super.hashCode
      if (result == 0) {
        result = unknownFields.hashCode()
        result = result * 37 + segments.hashCode()
        super.hashCode = result
      }
      return result
    }

    public override fun toString(): String {
      val result = mutableListOf<String>()
      if (segments.isNotEmpty()) result += """segments=${sanitize(segments)}"""
      return result.joinToString(prefix = "Name{", separator = ", ", postfix = "}")
    }

    public fun copy(segments: List<String> = this.segments, unknownFields: ByteString =
        this.unknownFields): Name = Name(segments, unknownFields)

    public companion object {
      @JvmField
      public val ADAPTER: ProtoAdapter<Name> = object : ProtoAdapter<Name>(
        FieldEncoding.LENGTH_DELIMITED, 
        Name::class, 
        "type.googleapis.com/com.coreman2200.ringstrings.data.protos.LocalProfileDataBundle.Name", 
        PROTO_3, 
        null, 
        "ringstrings.proto"
      ) {
        public override fun encodedSize(`value`: Name): Int {
          var size = value.unknownFields.size
          size += ProtoAdapter.STRING.asRepeated().encodedSizeWithTag(1, value.segments)
          return size
        }

        public override fun encode(writer: ProtoWriter, `value`: Name): Unit {
          ProtoAdapter.STRING.asRepeated().encodeWithTag(writer, 1, value.segments)
          writer.writeBytes(value.unknownFields)
        }

        public override fun encode(writer: ReverseProtoWriter, `value`: Name): Unit {
          writer.writeBytes(value.unknownFields)
          ProtoAdapter.STRING.asRepeated().encodeWithTag(writer, 1, value.segments)
        }

        public override fun decode(reader: ProtoReader): Name {
          val segments = mutableListOf<String>()
          val unknownFields = reader.forEachTag { tag ->
            when (tag) {
              1 -> segments.add(ProtoAdapter.STRING.decode(reader))
              else -> reader.readUnknownField(tag)
            }
          }
          return Name(
            segments = segments,
            unknownFields = unknownFields
          )
        }

        public override fun redact(`value`: Name): Name = value.copy(
          unknownFields = ByteString.EMPTY
        )
      }

      @JvmField
      public val CREATOR: Parcelable.Creator<Name> = AndroidMessage.newCreator(ADAPTER)

      private const val serialVersionUID: Long = 0L
    }
  }
}

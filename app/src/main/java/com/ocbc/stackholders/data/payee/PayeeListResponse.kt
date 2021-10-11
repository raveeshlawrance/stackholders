package com.ocbc.stackholders.data.payee

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

class PayeeListResponse(
	val data: List<DataItem?>? = null,
	val status: String? = null
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.createTypedArrayList(DataItem.CREATOR),
		parcel.readString()
	) {
	}

	override fun describeContents(): Int {
		TODO("Not yet implemented")
	}

	override fun writeToParcel(p0: Parcel?, p1: Int) {
		p0?.writeTypedList(data)
		p0?.writeString(status)

	}

	companion object CREATOR : Parcelable.Creator<PayeeListResponse> {
		override fun createFromParcel(parcel: Parcel): PayeeListResponse {
			return PayeeListResponse(parcel)
		}

		override fun newArray(size: Int): Array<PayeeListResponse?> {
			return arrayOfNulls(size)
		}
	}
}

class DataItem(
	val accountNo: String? = null,
	val id: String? = null,
	val accountHolderName: String? = null
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readString(),
		parcel.readString()
	) {
	}

	override fun describeContents(): Int {
		TODO("Not yet implemented")
	}

	override fun writeToParcel(p0: Parcel?, p1: Int) {
		p0?.writeString(accountNo)
		p0?.writeString(id)
		p0?.writeString(accountHolderName)
	}

	companion object CREATOR : Parcelable.Creator<DataItem> {
		override fun createFromParcel(parcel: Parcel): DataItem {
			return DataItem(parcel)
		}

		override fun newArray(size: Int): Array<DataItem?> {
			return arrayOfNulls(size)
		}
	}
}

package com.capstone.nutrilens.data.response

import com.google.gson.annotations.SerializedName

data class ScanningTestingResponse(

	@field:SerializedName("karbohidrat")
	val karbohidrat: Any,

	@field:SerializedName("protein")
	val protein: Any,

	@field:SerializedName("serat")
	val serat: Any,

	@field:SerializedName("natrium")
	val natrium: Any,

	@field:SerializedName("takaran_saji")
	val takaranSaji: Int,

	@field:SerializedName("energi")
	val energi: Any,

	@field:SerializedName("lemak")
	val lemak: Any
)

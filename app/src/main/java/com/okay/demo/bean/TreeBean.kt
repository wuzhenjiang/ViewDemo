package com.okay.demo.bean

/**
 *@author wzj
 *@date 3/31/21 4:59 PM
 */
data class TreeBean(
    var id: Int,
    var level: Int,
    var layoutId: Int?,
    var content: String?,
    var isOpen: Boolean,
    var subTreeBean: ArrayList<TreeBean>?
)
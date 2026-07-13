package com.bq.zowi.models

class ZowiName(private val name: String) {

    override fun toString(): String = name

    companion object {
        private const val FACTORY_ZOWI_NAME = "#"

        @JvmStatic
        fun isFactoryName(name: String): Boolean = name == FACTORY_ZOWI_NAME

        @JvmStatic
        fun getFactoryName(): String = FACTORY_ZOWI_NAME
    }
}

package test.trendingrepos.main

/**
 * Generic segue implementation.
 * Could be enriched with animation and data parameters
 *
 * Created on 22/02/2018
 * @author sdelaysam
 */

enum class SegueType {
    SHOW_REPOS,
    OPEN_DETAILS,
    GO_BACK
}

data class Segue(val type: SegueType, var processed: Boolean = false)
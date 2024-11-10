package store

import camp.nextstep.edu.missionutils.test.NsTest
import camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PromotionTest : NsTest() {
    @Test
    fun `프로모션 재고 부족`() {
        assertSimpleTest {
            run("[감자칩-5]", "N", "N", "N")
            assertThat(output()).contains("프로모션 할인이 적용되지 않습니다.")
        }
    }

    @Test
    fun `프로모션 적용 가능`() {
        assertSimpleTest {
            run("[감자칩-3]", "N", "N", "N")
            assertThat(output()).contains("무료로 더 받을 수 있습니다.")
        }
    }

    override fun runMain() {
        main()
    }
}
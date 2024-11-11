package store

import camp.nextstep.edu.missionutils.test.NsTest
import camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StorageTest: NsTest() {
    @Test
    fun `중복 상품 요청(재고 초과)`() {
        assertSimpleTest {
            run("[감자칩-5],[감자칩-6]", "[감자칩-2]", "N", "N", "N")
            assertThat(output()).contains("재고 수량을 초과하여 구매할 수 없습니다.")
        }
    }

    @Test
    fun `중복 상품 요청(재고 내)`() {
        assertSimpleTest {
            run("[정식도시락-2],[정식도시락-3]", "N", "N", "N")
            assertThat(output()).contains("정식도시락\t\t5")
        }
    }

    override fun runMain() {
        main()
    }
}
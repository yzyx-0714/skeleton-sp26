import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Arrays;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntListOptionalTests {
    // TBD
    @Test
    public void doubledTest() {
        assertThat(IntList.doubled(null)).isNull();
        IntList L = new IntList(1, new IntList(2, new IntList(3, null)));
        IntList r = IntList.doubled(L);
        assertThat(r.first).isEqualTo(2);
        assertThat(r.rest.first).isEqualTo(4);
        assertThat(r.rest.rest.first).isEqualTo(6);
        assertThat(r.rest.rest.rest).isNull();
        assertThat(L.first).isEqualTo(1);
        assertThat(L.rest.first).isEqualTo(2);
        assertThat(L.rest.rest.first).isEqualTo(3);
        IntList r2 = IntList.doubled(new IntList(0, new IntList(-5, null)));
        assertThat(r2.first).isEqualTo(0);
        assertThat(r2.rest.first).isEqualTo(-10);
        assertThat(r2.rest.rest).isNull();
    }
}

package org.isolution.androtelescope.server.domain.imageCaching.impl;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * User: agwibowo
 * Date: 11/06/11
 * Time: 6:58 PM
 */
public class InMemoryImageCacheUnitTest {

    private InMemoryImageCache cache;

    @Before
    public void setup() {
        cache = new InMemoryImageCache();
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_fail_to_save_when_given_empty_bytes() {
        cache.save(new byte[]{});

    }

    @Test(expected = NullPointerException.class)
    public void should_fail_to_save_when_given_null_bytes() {
        cache.save(null);
    }

    @Test
    public void retrieve_should_return_null_when_nothing_has_been_saved_before() {
        assertThat(cache.retrieve(), is(nullValue()));
    }


    @Test
    public void should_be_able_to_retrieve_bytes_that_were_saved_in_the_past() {
        cache.save(new byte[]{11, 22});

        byte[] retrieved = cache.retrieve();
        assertThat(retrieved.length, is(2));
        assertThat(retrieved[0], is((byte)11));
        assertThat(retrieved[1], is((byte)22));
    }


    @Test
    public void save_should_override_previously_saved_bytes() {
        cache.save(new byte[]{11, 22});
        cache.save(new byte[]{99, 33, 44});

        byte[] retrieved = cache.retrieve();
        assertThat(retrieved.length, is(3));
        assertThat(retrieved[0], is((byte)99));
        assertThat(retrieved[1], is((byte)33));
        assertThat(retrieved[2], is((byte)44));
    }
}

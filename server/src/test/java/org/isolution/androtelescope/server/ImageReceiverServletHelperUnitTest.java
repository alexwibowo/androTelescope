package org.isolution.androtelescope.server;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * User: agwibowo
 * Date: 11/06/11
 * Time: 7:33 PM
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({IOUtils.class, ImageReceiverServletHelper.class})
public class ImageReceiverServletHelperUnitTest {

    @Mock
    private InputStream mockInputStream;

    private byte[] inputStreamBytes = new byte[]{0, 1, 2};

    private ImageReceiverServletHelper helper;

    private ImageRequestValidator mockValidator;

    @Before
    public void setup() throws Exception {
        helper = new ImageReceiverServletHelper();

        PowerMockito.mockStatic(IOUtils.class);
        when(IOUtils.toByteArray(mockInputStream)).thenReturn(inputStreamBytes);


        mockValidator = PowerMockito.mock(ImageRequestValidator.class);
        whenNew(ImageRequestValidator.class)
                .withArguments(Mockito.anyString(), Mockito.<Object>any())
                .thenReturn(mockValidator);

    }

    @Test
    public void should_return_the_bytes_read_from_the_inputStream() throws Exception {
        byte[] result = helper.readBytes(mockInputStream, "image/jpeg");
        assertThat(result.length, is(inputStreamBytes.length));
        assertThat(result[0], is((byte) 0));
        assertThat(result[1], is((byte) 1));
        assertThat(result[2], is((byte) 2));

        PowerMockito.verifyStatic();
        IOUtils.toByteArray(mockInputStream);
    }

    @Test
    public void should_validate_the_bytes_read_from_the_inputStream() throws Exception {
        helper.readBytes(mockInputStream, "image/jpeg");

        verifyNew(ImageRequestValidator.class).withArguments("image/jpeg", inputStreamBytes);
        verify(mockValidator, times(1)).validate();
    }


    @Test
    public void should_fail_when_validation_of_the_bytes_failed() throws IOException, InvalidImageRequestException {
        InvalidImageRequestException ex = new InvalidImageRequestException("hey hey, you cant escape!", 205);
        doThrow(ex).when(mockValidator).validate();

        try {
            helper.readBytes(mockInputStream, "image/jpeg");
            fail("Should have failed, since validation failed");
        } catch (Exception e) {
            assertThat((InvalidImageRequestException) e, org.hamcrest.Matchers.is(ex));
        }
    }

    @Test
    public void should_fail_when_unable_to_read_from_the_inputStream() throws IOException {
        IOException ioe = new IOException("we are sinking");
        PowerMockito.doThrow(ioe).when(IOUtils.class);
        IOUtils.toByteArray(any(InputStream.class));

        try {
            helper.readBytes(mockInputStream, "image/jpeg");
            fail("Should have failed, since unable to read from the inputstream");
        } catch (Exception e) {
            assertThat((IOException) e, org.hamcrest.Matchers.is(ioe));
        }
    }

}

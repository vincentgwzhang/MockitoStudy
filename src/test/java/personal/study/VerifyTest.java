package personal.study;

import com.google.common.collect.Lists;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InOrder;
import org.mockito.Mockito;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import personal.study.entity.MyList;

import java.util.List;

public class VerifyTest {

    // tests

    @Test
    public final void givenInteractionWithMockOccurred_whenVerifyingInteraction_thenCorrect() {
        final List<String> mockedList = mock(MyList.class);
        mockedList.size();
        verify(mockedList).size();
    }

    @Test
    public final void givenOneInteractionWithMockOccurred_whenVerifyingNumberOfInteractions_thenCorrect() {
        final List<String> mockedList = mock(MyList.class);
        mockedList.size();
        verify(mockedList, times(1)).size();
    }

    @Test
    public final void givenNoInteractionWithMockOccurred_whenVerifyingInteractions_thenCorrect() {
        final List<String> mockedList = mock(MyList.class);
        verifyZeroInteractions(mockedList);
    }

    @Test
    public final void givenNoInteractionWithMethodOfMockOccurred_whenVerifyingInteractions_thenCorrect() {
        final List<String> mockedList = mock(MyList.class);
        verify(mockedList, times(0)).size();
    }

    @Test(expected = NoInteractionsWanted.class)
    public final void givenUnverifiedInteraction_whenVerifyingNoUnexpectedInteractions_thenFail() {
        final List<String> mockedList = mock(MyList.class);
        mockedList.size();
        mockedList.clear();

        verify(mockedList).size();
        verifyNoMoreInteractions(mockedList);
    }

    @Test
    public final void whenVerifyingOrderOfInteractions_thenCorrect() {
        final List<String> mockedList = mock(MyList.class);
        mockedList.size();
        mockedList.add("a parameter");
        mockedList.clear();

        final InOrder inOrder = Mockito.inOrder(mockedList);
        inOrder.verify(mockedList).size();
        inOrder.verify(mockedList).add("a parameter");
        inOrder.verify(mockedList).clear();
    }

    @Test
    public final void whenVerifyingAnInteractionHasNotOccurred_thenCorrect() {
        final List<String> mockedList = mock(MyList.class);
        mockedList.size();

        verify(mockedList, never()).clear();
    }

    @Test
    public final void whenVerifyingAnInteractionHasOccurredAtLeastOnce_thenCorrect() {
        final List<String> mockedList = mock(MyList.class);
        mockedList.clear();
        mockedList.clear();
        mockedList.clear();

        verify(mockedList, atLeast(1)).clear();
        verify(mockedList, atMost(10)).clear();
    }

    // with arguments

    @Test
    public final void whenVerifyingAnInteractionWithExactArgument_thenCorrect() {
        final List<String> mockedList = mock(MyList.class);
        mockedList.add("test");

        verify(mockedList).add("test");
    }

    @Test
    public final void whenVerifyingAnInteractionWithAnyArgument_thenCorrect() {
        final List<String> mockedList = mock(MyList.class);
        mockedList.add("test");

        verify(mockedList).add(ArgumentMatchers.anyString());
    }

    @Test
    public final void whenVerifyingAnInteractionWithArgumentCapture_thenCorrect() {
        final List<String> mockedList = mock(MyList.class);
        mockedList.addAll(Lists.<String>newArrayList("someElement"));
        final ArgumentCaptor<List> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(mockedList).addAll(argumentCaptor.capture());
        final List<String> capturedArgument = argumentCaptor.<List<String>>getValue();
        assertThat(capturedArgument, hasItem("someElement"));
    }

}

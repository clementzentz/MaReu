package clement.zentz.mareu.service;

import org.hamcrest.collection.IsArrayContainingInOrder;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import clement.zentz.mareu.ReunionActivity;
import clement.zentz.mareu.di.DI;
import clement.zentz.mareu.models.Reunion;
import clement.zentz.mareu.service.util.ListTest;

import static clement.zentz.mareu.service.FakeReunionGenerator.salle2;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class FakeReunionApiServiceTest {

    private ReunionApiService mService;
    private Reunion reunionTest;

    @Before
    public void setup(){
        mService = DI.getNewInstanceApiService();
        reunionTest = new Reunion("11:10","02/03/1996", salle2, "AWS", "andrea@gmail.com",false);
    }

    @Test
    public void getReunions() {
        //Arrange
        List<Reunion> reunions = mService.getReunions();
        //Act
        List<Reunion> expectedReunions = FakeReunionGenerator.FAKE_REUNIONS;
        //Assert
        assertThat(reunions, IsIterableContainingInAnyOrder.containsInAnyOrder(Objects.requireNonNull(expectedReunions.toArray())));
    }

    @Test
    public void addReunion() {
        //Arrange
        Reunion reunionToAdd = reunionTest;
        //Act
        mService.addReunion(reunionToAdd);
        //Assert
        assertTrue(mService.getReunions().contains(reunionToAdd));
    }


    //créer soit même une réunion assertion false
    //tester le système de filtre et de
    @Test
    public void deleteReunion() {
        //Arrange
        Reunion reunionToDelete = mService.getReunions().get(0);
        //Act
        mService.deleteReunion(reunionToDelete);
        //Assert
        assertFalse(mService.getReunions().contains(reunionToDelete));
    }

    @Test
    public void updateReunion() {
        //Arrange
        Reunion reunionToUpdate = mService.getReunions().get(0);
        //Act
        reunionToUpdate.setHeureReunion("11:00");
        mService.updateReunion(reunionToUpdate,0);
        //Assert
        assertEquals(mService.getReunions().get(0).getHeureReunion(), reunionToUpdate.getHeureReunion());
    }

    @Test
    public void testSortReunionsByDate() {
        //Arrange
        List<Reunion> actual = mService.getReunions();
        List<Reunion> expected = ListTest.getReunionsTestSortedByDate();
        //Act
        Collections.sort(actual, new ReunionActivity.ComparatorDateReu());
        //Assert
        assertThat(actual.toArray(), IsArrayContainingInOrder.arrayContaining((expected.toArray())));
        assertArrayEquals(actual.toArray(), expected.toArray());
    }

    @Test
    public void testSortReunionsByLieu() {
        //Arrange
        List<Reunion> actual = mService.getReunions();
        List<Reunion> expected = ListTest.getReunionsTestSortedByLieu();
        //Act
        Collections.sort(actual, new ReunionActivity.ComparatorLieuReu());
        //Assert
        assertThat(actual.toArray(), IsArrayContainingInOrder.arrayContaining((expected.toArray())));
        assertArrayEquals(actual.toArray(), expected.toArray());
    }

    @Test
    public void testSortReunionsReset() {
        //Arrange
        List<Reunion> actual = mService.getReunions();
        List<Reunion> expected = ListTest.getReunionsTestSortedReset();
        //Act
        Collections.sort(actual, new ReunionActivity.ComparatorSujetReu());
        //Assert
        assertThat(actual.toArray(), IsArrayContainingInOrder.arrayContaining((expected.toArray())));
        assertArrayEquals(actual.toArray(), expected.toArray());
    }
}
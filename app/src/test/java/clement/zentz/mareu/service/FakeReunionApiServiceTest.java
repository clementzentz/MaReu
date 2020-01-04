package clement.zentz.mareu.service;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

import clement.zentz.mareu.di.DI;
import clement.zentz.mareu.models.Reunion;

import static org.junit.Assert.*;

public class FakeReunionApiServiceTest {

    private ReunionApiService mService;

    @Before
    public void setup(){
        mService = DI.getNewInstanceApiService();
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
        Reunion reunionToAdd = mService.getReunions().get(0);
        //Act
        mService.addReunion(reunionToAdd);
        //Assert
        assertTrue(mService.getReunions().contains(reunionToAdd));
    }

    @Test
    public void deleteReunion() {
        //Arrange
        int indexReunionToDelete = mService.getReunions().indexOf(mService.getReunions().get(0));
        //Act
        mService.deleteReunion(indexReunionToDelete);
        //Assert
        assertFalse(mService.getReunions().contains(indexReunionToDelete));
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
}
package mirdep.br.mykwad;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import mirdep.br.mykwad.pecas.Antena;
import mirdep.br.mykwad.pecasRepositorio.AntenaRepositorio;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_tab1, R.id.navigation_tab2, R.id.navigation_tab3).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        initialize();
    }

    private void initialize(){
        Antena antena = new Antena("TBS","Triumph",35.25,"SMA");
        antena.setId("1");
        new AntenaRepositorio().salvarAntena(antena);
    }

}

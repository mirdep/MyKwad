package mirdep.br.mykwad;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import mirdep.br.mykwad.comum.MetodosGerais;
import mirdep.br.mykwad.pecas.Antena;
import mirdep.br.mykwad.pecasRepositorio.AntenaRepositorio;
import mirdep.br.mykwad.ui.tabComunidade.ComunidadeFragment;
import mirdep.br.mykwad.ui.tabCriarDrone.CriarDroneFragment;
import mirdep.br.mykwad.ui.tabMinhaConta.MinhaContaFragment;
import mirdep.br.mykwad.usuario.AutenticacaoRepositorio;
import mirdep.br.mykwad.usuario.LoginFragment;

public class BaseApp extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_app);

        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);

//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_criar, R.id.navigation_comunidade, R.id.navigation_minhaconta).build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//
//
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);

        //initialize();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_criar: {
                getSupportActionBar().setTitle("Criar drone");
                Fragment fragment = new CriarDroneFragment();
                openFragment(fragment);
                break;
            }
            case R.id.navigation_comunidade: {
                getSupportActionBar().setTitle("Comunidade");
                Fragment fragment = new ComunidadeFragment();
                openFragment(fragment);
                break;
            }
            case R.id.navigation_minhaconta: {
                abrirMinhaConta();
                break;
            }
        }
        return true;
    }

    private void abrirMinhaConta(){
        FirebaseUser user = AutenticacaoRepositorio.getUsuario();
        Fragment fragment;
        if(user != null){
            getSupportActionBar().setTitle("Minha conta");
            fragment = new MinhaContaFragment();
        } else {
            getSupportActionBar().setTitle("Login");
            fragment = new LoginFragment();
        }
        openFragment(fragment);
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void initialize() {
        Antena antena = new Antena("TBS", "ha", 35.25, "SMA");
        antena.setId(MetodosGerais.encodeId("abcde"));
        new AntenaRepositorio().salvarAntena(antena, false);
    }


}

package mirdep.br.mykwad.fragments.tabMinhaConta;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;

import mirdep.br.mykwad.BaseApp;
import mirdep.br.mykwad.R;
import mirdep.br.mykwad.comum.MyDialog;
import mirdep.br.mykwad.repositorio.GlideApp;
import mirdep.br.mykwad.repositorio.ImagemRepositorio;
import mirdep.br.mykwad.repositorio.UsuarioAuthentication;
import mirdep.br.mykwad.ui.DronesFavoritosAdapter;
import mirdep.br.mykwad.ui.MeusDronesAdapter;
import mirdep.br.mykwad.ui.VerticalSpaceItemDecoration;
import mirdep.br.mykwad.viewmodels.MinhaContaViewModel;

public class MinhaContaFragment extends Fragment {

    private View root;

    public MinhaContaViewModel mViewModel;

    private RecyclerView recyclerView;
    private MeusDronesAdapter meusDronesAdapter;
    private DronesFavoritosAdapter dronesFavoritosAdapter;

    private TextView textView_usuario_nome;
    private TextView textView_usuario_email;
    private TextView textView_usuario_nickname;

    private View viewButton_minhaconta_logout;
    private View viewButton_minhaconta_editar;

    private ImageView imageView_usuario_foto;

    private ProgressDialog loadingDialog;

    private TabLayout tabs;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_minhaconta, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        root.findViewById(R.id.loadingIcone).setVisibility(View.GONE);
        mViewModel = ViewModelProviders.of(this).get(MinhaContaViewModel.class);
        loadingDialog = MyDialog.criarProgressDialog(root.getContext(),"Carregando dados...");
        loadingDialog.show();
        inicializarInterface();
        adicionarListeners();
        atualizarTela();
        tabs.getTabAt(0).select();
    }

    private void inicializarInterface() {
        tabs = root.findViewById(R.id.tabs);
        inicializarMeusDronesRecyclerView();
        textView_usuario_email = root.findViewById(R.id.textView_usuario_email);
        textView_usuario_email.setText("");
        textView_usuario_nome = root.findViewById(R.id.textView_usuario_nome);
        textView_usuario_nome.setText("");
        textView_usuario_nickname = root.findViewById(R.id.textView_usuario_nickname);
        textView_usuario_nickname.setText("");

        imageView_usuario_foto = root.findViewById(R.id.imageView_usuario_foto);

        viewButton_minhaconta_logout = root.findViewById(R.id.viewButton_minhaconta_logout);
        viewButton_minhaconta_editar = root.findViewById(R.id.viewButton_minhaconta_editar);
    }

    private void adicionarListeners() {
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    inicializarMeusDronesRecyclerView();
                } else {
                    inicializarDronesFavoritosRecyclerView();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewButton_minhaconta_logout.setOnClickListener(v -> {
            UsuarioAuthentication.getInstance().logoutConta();
            ((BaseApp) getActivity()).abrirTabMinhaConta();
        });

        viewButton_minhaconta_editar.setOnClickListener(v -> {
            abrirEditarContaFragment();
        });
    }

    //Iniciailiza o recyclerView
    private void inicializarMeusDronesRecyclerView() {
        meusDronesAdapter = new MeusDronesAdapter(this);
        povoarMeusDronesAdapter();
        recyclerView = root.findViewById(R.id.recyclerViewDrones);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(10));
        recyclerView.setAdapter(meusDronesAdapter);

    }

    //Iniciailiza o recyclerView
    private void inicializarDronesFavoritosRecyclerView() {
        dronesFavoritosAdapter = new DronesFavoritosAdapter(this);
        povoarDronesFavoritosAdapter();
        recyclerView = root.findViewById(R.id.recyclerViewDrones);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(10));
        recyclerView.setAdapter(dronesFavoritosAdapter);

    }

    //Coloca a lista de peças no meusDronesAdapter do recyclewView
    private void povoarMeusDronesAdapter() {
        mViewModel.getDronesDoUsuario(drones -> {
            meusDronesAdapter.definirDrones(drones);
            if(drones.size() == 0) root.findViewById(R.id.loadingIcone).setVisibility(View.GONE);
        });
    }

    //Coloca a lista de peças no dronesFavoritosAdapter do recyclewView
    private void povoarDronesFavoritosAdapter() {
        mViewModel.getDronesFavoritos(drones -> {
            dronesFavoritosAdapter.definirDrones(drones);
            if(drones.size() == 0) root.findViewById(R.id.loadingIcone).setVisibility(View.GONE);
        });
    }

    private void atualizarTela() {
        mViewModel.getUsuarioAtual(usuario -> {
            textView_usuario_nickname.setText(usuario.getNickname());
            textView_usuario_nome.setText(usuario.getNome());
            textView_usuario_email.setText(usuario.getEmail());
            carregarFoto(usuario.getId());
            loadingDialog.dismiss();
        });
    }

    private void carregarFoto(String idUsuario) {
        Drawable defaultFoto = getResources().getDrawable(R.drawable.profile);
        GlideApp.with(root.getContext())
                .load(ImagemRepositorio.getInstance().getFotoUsuarioReference(idUsuario))
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .error(defaultFoto)
                .into(imageView_usuario_foto);
    }

    private void abrirEditarContaFragment(){
        openFragment(new EditarContaFragment());
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = this.getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
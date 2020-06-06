package mirdep.br.mykwad.main_tabs.tabMinhaConta;

import android.app.ProgressDialog;
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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import mirdep.br.mykwad.BaseApp;
import mirdep.br.mykwad.R;
import mirdep.br.mykwad.comum.Configs;
import mirdep.br.mykwad.comum.MyDialog;
import mirdep.br.mykwad.objetos.Usuario;
import mirdep.br.mykwad.repositorio.GlideApp;
import mirdep.br.mykwad.repositorio.UsuarioAuthentication;
import mirdep.br.mykwad.repositorio.UsuarioRepositorio;
import mirdep.br.mykwad.ui.ExibirDronesAdapter;
import mirdep.br.mykwad.ui.VerticalSpaceItemDecoration;

public class MinhaContaFragment extends Fragment {

    private Usuario usuario;

    private View root;

    public MinhaContaViewModel mViewModel;

    private RecyclerView recyclerView;
    private ExibirDronesAdapter adapter;

    private EditarContaFragment editarContaDialog;

    private TextView textView_usuario_nome;
    private TextView textView_usuario_email;
    private TextView textView_usuario_nickname;

    private View viewButton_minhaconta_logout;
    private View viewButton_minhaconta_editar;

    private ImageView imageView_usuario_foto;

    private ProgressDialog loadingDialog;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_minhaconta, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MinhaContaViewModel.class);
        loadingDialog = MyDialog.criarProgressDialog(root.getContext(),"Carregando dados...");
        loadingDialog.show();
        inicializarInterface();
        adicionarListeners();
        inicializarRecyclerView();
        atualizarTela();
    }

    private void inicializarInterface() {
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
        viewButton_minhaconta_logout.setOnClickListener(v -> {
            UsuarioAuthentication.getInstance().logoutConta();
            ((BaseApp) getActivity()).abrirTabMinhaConta();
        });

        viewButton_minhaconta_editar.setOnClickListener(v -> {
            abrirEditarContaFragment();
        });
    }

    //Iniciailiza o recyclerView
    private void inicializarRecyclerView() {
        adapter = new ExibirDronesAdapter(this);
        povoarAdapter();
        recyclerView = root.findViewById(R.id.recyclerViewDrones);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(10));
        recyclerView.setAdapter(adapter);

    }

    //Coloca a lista de peças no adapter do recyclewView
    private void povoarAdapter() {
        mViewModel.getDronesDoUsuario().observe(getViewLifecycleOwner(), drones -> {
            adapter.definirDrones(drones);
            root.findViewById(R.id.loadingIcone).setVisibility(View.GONE);
        });
    }

    private void atualizarTela() {
        final LiveData<Usuario> usuarioInfo = mViewModel.getUsuario();
        usuarioInfo.observe(getViewLifecycleOwner(), exec -> {
            usuario = usuarioInfo.getValue();
            textView_usuario_nickname.setText(usuario.getNickname());
            textView_usuario_nome.setText(usuario.getNome());
            textView_usuario_email.setText(usuario.getEmail());
            carregarFoto();
            loadingDialog.dismiss();
        });
    }

    private void carregarFoto() {
        GlideApp.with(getContext())
                .load(UsuarioRepositorio.getInstance().getStorageReference().child(usuario.getId()+ Configs.EXTENSAO_IMAGEM))
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
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
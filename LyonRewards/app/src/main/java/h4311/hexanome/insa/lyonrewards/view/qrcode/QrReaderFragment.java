package h4311.hexanome.insa.lyonrewards.view.qrcode;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;
import h4311.hexanome.insa.lyonrewards.R;

/**
 * Created by Jonathan on 27/04/2016.
 */
public class QrReaderFragment extends Fragment {

    private OnQrCodeFoundListener mCallback;

    @BindView(R.id.camera_view)
    protected SurfaceView mSurfaceView;

    private boolean processingQrCode;

    public static QrReaderFragment newInstance(Bundle bundle) {
        QrReaderFragment fragment = new QrReaderFragment();
        fragment.setArguments(bundle);

        QREader.getInstance().setUpConfig();

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnQrCodeFoundListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnQrCodeFoundListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qr_reader, container, false);
        ButterKnife.bind(this, view);

        processingQrCode = false;

        // Start QREader
        QREader.getInstance().start(getActivity().getApplicationContext(), mSurfaceView, new QRDataListener() {
            @Override
            public void onDetected(final String data) {
                Log.d("QREader", "Value : " + data);

                if(!processingQrCode && mCallback != null) {
                    mCallback.onQrCodeFound(data);
                    processingQrCode = true;
                }
            }
        });

        return view;
    }

    private void showQrCodeFoundDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        //alertDialog.setTitle(R.string.sure);
        alertDialog.setMessage(getString(R.string.scanner_qrcode_found));
//        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.confirm),
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancel),
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });

        alertDialog.show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

package h4311.hexanome.insa.lyonrewards.view.qrcode;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;

import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.android.gms.vision.CameraSource;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;
import h4311.hexanome.insa.lyonrewards.R;

/**
 * Created by Jonathan on 27/04/2016.
 */
public class QrReaderFragment extends Fragment {

    private static final String QR_CODE_NOT_RECOGNIZED = "Ce QR code n'est pas reconnu par l'application";

    private OnQrCodeFoundListener mCallback;

    @BindView(R.id.camera_view)
    protected SurfaceView mSurfaceView;

    private boolean processingQrCode;

    private String lastValue;

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

//        DisplayMetrics metrics = new DisplayMetrics();
//        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
//        wm.getDefaultDisplay().getMetrics(metrics);
//
//        mSurfaceView.setMinimumWidth(metrics.widthPixels);
//        mSurfaceView.setMinimumHeight(metrics.heightPixels);
//
//        QREader.getInstance().setUpConfig(true, metrics.widthPixels, metrics.heightPixels, CameraSource.CAMERA_FACING_BACK);

        // Start QREader
        QREader.getInstance().start(getActivity().getApplicationContext(), mSurfaceView, new QRDataListener() {
            @Override
            public void onDetected(final String data) {
                if(data.equals(lastValue)) {
                    return;
                }

                Log.d("QREader", "Value : " + data);

                if (!processingQrCode && mCallback != null) {
                    QrCodeContent qrCodeContent = getQrCodeContent(data);
                    if (qrCodeContent != null) {
                        processingQrCode = true;
                        mCallback.onQrCodeFound(qrCodeContent);
                    }
                    else {
                        Snackbar.make(getView(), QR_CODE_NOT_RECOGNIZED, Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                }
                lastValue = data;
            }
        });

        return view;
    }

    private QrCodeContent getQrCodeContent(String qrCodeValue) {
        Pattern qrCodePattern = Pattern.compile("(\\d+)&(\\d+)");
        Matcher m = qrCodePattern.matcher(qrCodeValue);
        if (m.matches()) {
            int actId = Integer.parseInt(m.group(1));
            int eventId = Integer.parseInt(m.group(2));

            Log.d("QR_CODE_PARSER", "ActId : " + actId);
            Log.d("QR_CODE_PARSER", "EventId : " + eventId);

            return new QrCodeContent(qrCodeValue, actId, eventId);
        }
        return null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

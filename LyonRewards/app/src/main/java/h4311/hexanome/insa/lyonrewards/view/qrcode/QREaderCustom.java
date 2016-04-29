package h4311.hexanome.insa.lyonrewards.view.qrcode;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;

/**
 * Created by Jonathan on 29/04/2016.
 */
public class QREaderCustom {

    private final String TAG = "QREader";
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;

    private boolean autofocus_enabled;
    private int width;
    private int height;
    private int facing;

    private static QREaderCustom INSTANCE;

    private QREaderCustom() {

    }

    public static QREaderCustom getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new QREaderCustom();
        }
        return INSTANCE;
    }

    public void setUpConfig() {
        setUpConfig(true, 800, 800, CameraSource.CAMERA_FACING_BACK);
    }

    public void setUpConfig(boolean autofocus_enabled, int facing) {
        setUpConfig(autofocus_enabled, 800, 800, facing);
    }

    public void setUpConfig(boolean autofocus_enabled, int width, int height, int facing) {
        this.autofocus_enabled = autofocus_enabled;
        this.width = width;
        this.height = height;
        this.facing = facing;
    }


    public void start(final Context context, final SurfaceView surfaceView, final QRDataListener QRDataListener) {
        barcodeDetector =
                new BarcodeDetector.Builder(context)
                        .setBarcodeFormats(Barcode.QR_CODE)
                        .build();

        cameraSource = new CameraSource
                .Builder(context, barcodeDetector)
                .setAutoFocusEnabled(autofocus_enabled)
                .setFacing(facing)
                .setRequestedPreviewSize(width, height)
                .build();

        surfaceView.getHolder()
                .addCallback(new SurfaceHolder.Callback() {
                    @Override
                    public void surfaceCreated(SurfaceHolder holder) {
                        startCameraView(context, cameraSource, surfaceView);
                    }

                    @Override
                    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                    }

                    @Override
                    public void surfaceDestroyed(SurfaceHolder holder) {
                        startCameraView(context, cameraSource, surfaceView);
                    }
                });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                    QRDataListener.onDetected(barcodes.valueAt(0).displayValue);
                }
            }
        });

    }

    public void stop() {
        cameraSource.stop();
    }

    private void startCameraView(Context context, CameraSource cameraSource, SurfaceView
            surfaceView) {
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                cameraSource.start(surfaceView.getHolder());
            } else {
                Log.e(TAG, "Permission not granted!");
            }
        } catch (IOException ie) {
            Log.e(TAG, ie.getMessage());
            ie.printStackTrace();
        }

    }
}

package org.firstinspires.ftc.teamcode.TeleOp;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
//@Disabled
public class MechaHardware {

    public DcMotor  RL        = null;
    public DcMotor  RR        = null;
    public DcMotor  FL        = null;
    public DcMotor  FR        = null;
    public DcMotor  SLift     = null;
    public DcMotor  STilt     = null;
    public Servo    GrabTilt   = null;
    public Servo    Grabber    = null;
    public Servo    Launcher    = null;


    /* Constructor */
    public MechaHardware(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        /* local OpMode members. */


        RL         = ahwMap.get(DcMotor.class, "RL"        );
        RR         = ahwMap.get(DcMotor.class, "RR"        );
        FL         = ahwMap.get(DcMotor.class, "FL"        );
        FR         = ahwMap.get(DcMotor.class, "FR"        );
        SLift      = ahwMap.get(DcMotor.class, "SLift"     );
        STilt      = ahwMap.get(DcMotor.class, "STilt"     );
        Grabber    = ahwMap.servo.get(                    "Grabber"   );
        GrabTilt   = ahwMap.servo.get(                    "GrabTilt"  );
        Launcher   = ahwMap.servo.get(                    "Launch"    );



        SLift.setDirection(DcMotor.Direction.FORWARD);
        RL.setDirection(DcMotorSimple.Direction.REVERSE);
        RR.setDirection(DcMotorSimple.Direction.FORWARD);
        FL.setDirection(DcMotorSimple.Direction.FORWARD);
        FR.setDirection(DcMotorSimple.Direction.REVERSE);

        RL.setPower(0);
        RR.setPower(0);
        FL.setPower(0);
        FR.setPower(0);
        SLift.setPower(0);
        STilt.setPower(0);



        // Set all motors to run without encoders.
        RL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        SLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        STilt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



        RL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        SLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        STilt.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);




    }

}

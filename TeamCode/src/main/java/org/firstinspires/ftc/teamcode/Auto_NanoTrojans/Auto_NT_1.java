/*
 * Copyright (c) 2022 Titan Robotics Club (http://www.titanrobotics.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.firstinspires.ftc.teamcode.Auto_NanoTrojans;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.controls_NanoTrojans;
import org.firstinspires.ftc.teamcode.resources_NanoTrojans;

/**
 * This class contains the Autonomous Mode program.
 */
@Config
@Autonomous(name = "Auto_NT_1")
public class Auto_NT_1 extends LinearOpMode {

    // Constants for encoder counts and wheel measurements
    //private controls_NanoTrojans g2control;
    //private resources_NanoTrojans resources;


    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());


        //just incase if we need use these resources: servo and motors
//        resources = new resources_NanoTrojans(hardwareMap);
//        g2control = new controls_NanoTrojans(resources.lsRight, resources.lsLeft, resources.claw,
//                resources.lhsl, resources.rhsl, resources.rintakelift, resources.lintakelift, resources.intakewheels, resources.casket);

        //Base driver instance
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        Pose2d beginPose = new Pose2d(0, 0, 0);
        drive.pose = beginPose;
        telemetry.addData("x", 5);
        telemetry.addData("y", 6);
        telemetry.update();

        boolean stop = false;
        waitForStart();

        while (opModeIsActive() && !stop) {

            //telemetry.addData("Blue Close Got position", position2);
            telemetry.addData("x", drive.pose.position.x);
            telemetry.addData("y", drive.pose.position.y);
            // telemetry.addData("heading (deg)", Math.toDegrees(drive.pose.heading.toDouble()));
            telemetry.update();
            Action trajectoryAction1= drive.actionBuilder(beginPose)
                    .splineTo(new Vector2d(30, 30), Math.PI / 2)
                    .splineTo(new Vector2d(0, 60), Math.PI)
                    .build();

            Actions.runBlocking(
                    new SequentialAction(
                            trajectoryAction1

                    )
            );

//            Actions.runBlocking(
//                    drive.actionBuilder(beginPose)
//                            .splineTo(new Vector2d(30, 30), Math.PI / 2)
//                            .splineTo(new Vector2d(0, 60), Math.PI)
//                            .build());
            telemetry.addData("x", 7);
            telemetry.addData("y", 8);
            // telemetry.addData("heading (deg)", Math.toDegrees(drive.pose.heading.toDouble()));
            telemetry.update();
            Actions.runBlocking(
                    drive.actionBuilder(new Pose2d(0, 0, 0))
                            .lineToX(10)
                            .lineToX(0)
                            .build());
            telemetry.addData("x", drive.pose.position.x);
            telemetry.addData("y", drive.pose.position.y);
            // telemetry.addData("heading (deg)", Math.toDegrees(drive.pose.heading.toDouble()));
            telemetry.update();

            stop = true;

        }//end of while loop
    }
}

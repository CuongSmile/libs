package com.base.project.widget.mediaplayer;

import java.io.File;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.base.project.util.DateTimeUtil;
import com.example.projectlibs.R;

public class SimpleControlMediaPlayer extends LinearLayout implements
		OnClickListener, OnPreparedListener, OnCompletionListener {

	private ImageView btnControl;
	private TextView txtLeft, txtRight;
	private SeekBar timer;
	private boolean isPlaying = false, lockButton = false;

	private MediaPlayer player;
	private String uri;
	private Handler monitorHandler = new Handler();

	public SimpleControlMediaPlayer(Context context) {
		super(context);
		this.init();
	}

	public boolean isLockButton() {
		return lockButton;
	}

	public void setLockButton(boolean lockButton) {
		this.lockButton = lockButton;
	}

	public SimpleControlMediaPlayer(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.init();
	}

	@SuppressLint("NewApi")
	public SimpleControlMediaPlayer(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		this.init();
	}

	private void init() {
		View.inflate(getContext(), R.layout.layout_meida_player, this);
		this.btnControl = (ImageView) this.findViewById(R.id.btnControl);
		// this.txtLeft = (TextView) this.findViewById(R.id.txtTimeLeft);
		this.txtLeft = (TextView) this.findViewById(R.id.txtTimeRight);
		this.timer = (SeekBar) this.findViewById(R.id.timer);
		this.timer.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});
		timer.setProgress(0);
		this.btnControl.setOnClickListener(this);

	}

	// public void setLinkResourceToPlayer(String link){
	// if(link != null){
	// this.player = new MediaPlayer();
	// this.player.setDataSource(link);
	// this.player.prepareAsync();
	// this.player.setOnPreparedListener(this);
	// this.player.setOnCompletionListener(this);
	// }
	// }

	public void setUriResourceToPlayer(String uri) {
		if (uri != null) {
			File f = new File(uri);
			if (f.exists()) {
				initPlayer(uri);
				this.uri = uri;
			}
		}
	}

	private void updateTime() {
		if (player != null) {
			int timeLeft = player.getCurrentPosition();
			int timeRight = player.getDuration() - timeLeft;
			this.updateTimeLeft(DateTimeUtil.getTime(timeLeft));
			this.updateTimeRight(DateTimeUtil.getTime(timeRight));
		}
	}

	private void initPlayer(String uri) {
		this.player = new MediaPlayer();
		try {
			this.player.setDataSource(new File(uri).getAbsolutePath());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.player.setOnPreparedListener(this);
		this.player.setOnCompletionListener(this);
		this.player.prepareAsync();

	}

	@Override
	public void onClick(View v) {
		if (!lockButton) {
			this.controlMedia();
		}
	}

	private void controlMedia() {
		if (isPlaying) {
			this.btnControl.setImageResource(R.drawable.ico_play);
			pauseMedia();
			isPlaying = false;
			// if (schule.isAlive()) {
			// schule.stop();
			// }
		} else {
			this.btnControl.setImageResource(R.drawable.ico_pause);
			playMedia();
			isPlaying = true;
			startUpdateTime();

		}

	}

	private void pauseMedia() {
		if (player != null) {
			this.player.pause();
		}
	}

	private void playMedia() {
		if (player != null) {
			this.player.start();
		}
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// mp.reset();
		isPlaying = false;
		this.btnControl.setImageResource(R.drawable.ico_play);

	}

	/**
	 * methos use control update seekbar time when media running
	 */
	public void updateProgressBar() {
		monitorHandler.postDelayed(mUpdateTimeTask, 100);
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		this.updateTimeLeft(DateTimeUtil.getTime(mp.getDuration()));
		this.timer.setMax(mp.getDuration());
		isPlaying = true;
		mp.start();
		startUpdateTime();
	}

	/**
	 * Background Runnable thread update seekbar time when media running
	 * */
	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
			// int totalDuration = mediaPlayer.getDuration();
			if (isPlaying) {
				updateTime();
				if (timer != null && player != null) {
					timer.setProgress(player.getCurrentPosition());
				}
				if (monitorHandler != null) {
					monitorHandler.postDelayed(this, 100);
				}
			}

		}
	};

	private void startUpdateTime() {
		this.btnControl.setImageResource(R.drawable.ico_pause);
		updateProgressBar();
		// if (!schule.isShutdown()) {
		// schule.shutdownNow();
		// }
		// schule.scheduleWithFixedDelay(new Runnable() {
		//
		// @Override
		// public void run() {
		// monitorHandler.sendMessage(monitorHandler.obtainMessage());
		//
		// }
		// }, 200, 200, TimeUnit.MILLISECONDS);
	}

	private void updateTimeLeft(String content) {
		this.updateTime(content, txtLeft);
	}

	private void updateTimeRight(String content) {
		this.updateTime(content, txtRight);
	}

	private void updateTime(String time, TextView txtTime) {
		if (txtTime != null) {
			txtTime.setText(time);
		}
	}

	public void onDestroy() {
		if (monitorHandler != null) {
			monitorHandler = null;
		}
		if (player != null) {
			player.reset();
			player.release();
			player = null;
		}
	}

}

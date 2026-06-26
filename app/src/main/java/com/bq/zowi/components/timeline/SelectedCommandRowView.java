package com.bq.zowi.components.timeline;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.bq.zowi.R;
import com.bq.zowi.components.MultipleStatesButton;
import com.bq.zowi.models.commands.Command;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class SelectedCommandRowView extends LinearLayout {
    private Command.Direction[] allowedDirections;
    private Long[] allowedDurations;
    private Command.Direction direction;
    private MultipleStatesButton<Command.Direction> directionTristateButton;
    private long duration;
    private MultipleStatesButton<Long> durationTristateButton;
    private Drawable iconDrawable;
    private ImageView iconImageView;
    private boolean isRepeatible;
    private ChangeListener listener;
    private int repetitions;
    private MultipleStatesButton<Integer> repetitionsTristateButton;

    public interface ChangeListener {
        void onDirectionChanged(Command.Direction direction);

        void onDurationChanged(long j);

        void onRepetitionsChanged(int i);
    }

    public SelectedCommandRowView(Context context) {
        super(context);
        this.repetitions = 1;
        init(context, null, 0);
    }

    public SelectedCommandRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.repetitions = 1;
        init(context, attrs, 0);
    }

    public SelectedCommandRowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.repetitions = 1;
        init(context, attrs, defStyleAttr);
    }

    public void setSelectedCommandRowViewChangesListener(ChangeListener listener) {
        this.listener = listener;
    }

    public void setIcon(Drawable iconDrawable) {
        if (iconDrawable != null) {
            this.iconDrawable = iconDrawable;
            invalidateIcon();
        }
    }

    public void setIsRepeatable(boolean isRepeatable) {
        if (this.isRepeatible != isRepeatable) {
            this.isRepeatible = isRepeatable;
            invalidateAllowedRepetitions();
        }
    }

    public void setAllowedDurations(Long[] allowedDurations) {
        if (allowedDurations != null && this.allowedDurations != allowedDurations) {
            this.allowedDurations = allowedDurations;
            invalidateAllowedDurations();
            if (allowedDurations.length > 0) {
                this.duration = allowedDurations[0].longValue();
            }
        }
    }

    public void setAllowedDirections(Command.Direction[] allowedDirections) {
        if (allowedDirections != null && this.allowedDirections != allowedDirections) {
            this.allowedDirections = allowedDirections;
            invalidateAllowedDirections();
            if (allowedDirections.length > 0) {
                this.direction = allowedDirections[0];
            }
        }
    }

    public void setDirection(Command.Direction direction) {
        if (direction != null) {
            this.direction = direction;
            this.directionTristateButton.setCurrentState(direction);
        }
    }

    public void setDuration(Long duration) {
        if (duration != null) {
            this.duration = duration.longValue();
            this.durationTristateButton.setCurrentState(duration);
        }
    }

    public void setRepetitions(Integer repetitions) {
        if (repetitions != null) {
            this.repetitions = repetitions.intValue();
            this.repetitionsTristateButton.setCurrentState(repetitions);
        }
    }

    public void disableMultipleStateButtons() {
        this.directionTristateButton.setEnabled(false);
        this.durationTristateButton.setEnabled(false);
        this.repetitionsTristateButton.setEnabled(false);
    }

    public void enableMultipleStateButtons() {
        this.directionTristateButton.setEnabled(true);
        this.durationTristateButton.setEnabled(true);
        this.repetitionsTristateButton.setEnabled(true);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        LayoutInflater.from(context).inflate(R.layout.component_selected_command_row_view, (ViewGroup) this, true);
        this.iconImageView = (ImageView) findViewById(R.id.selected_command_row_view_title_imageview);
        this.directionTristateButton = (MultipleStatesButton) findViewById(R.id.selected_command_row_view_direction_spinner);
        this.durationTristateButton = (MultipleStatesButton) findViewById(R.id.selected_command_row_view_duration_spinner);
        this.repetitionsTristateButton = (MultipleStatesButton) findViewById(R.id.selected_command_row_view_repetitions_spinner);
        HashMap<Integer, Drawable> repetitions = new HashMap<>();
        repetitions.put(10, ContextCompat.getDrawable(context, R.drawable.steps_10_button_selector));
        repetitions.put(5, ContextCompat.getDrawable(context, R.drawable.steps_5_button_selector));
        repetitions.put(1, ContextCompat.getDrawable(context, R.drawable.steps_1_button_selector));
        this.repetitionsTristateButton.setStates(repetitions, 1);
        this.repetitionsTristateButton.setOnStateChangedListener(new MultipleStatesButton.OnStateChangedListener<Integer>() { // from class: com.bq.zowi.components.timeline.SelectedCommandRowView.1
            @Override // com.bq.zowi.components.MultipleStatesButton.OnStateChangedListener
            public void onStateChanged(Integer repetitions2) {
                if (SelectedCommandRowView.this.listener != null) {
                    SelectedCommandRowView.this.listener.onRepetitionsChanged(repetitions2.intValue());
                }
            }
        });
        this.durationTristateButton.setOnStateChangedListener(new MultipleStatesButton.OnStateChangedListener<Long>() { // from class: com.bq.zowi.components.timeline.SelectedCommandRowView.2
            @Override // com.bq.zowi.components.MultipleStatesButton.OnStateChangedListener
            public void onStateChanged(Long duration) {
                if (SelectedCommandRowView.this.listener != null) {
                    SelectedCommandRowView.this.listener.onDurationChanged(duration.longValue());
                }
            }
        });
        this.directionTristateButton.setOnStateChangedListener(new MultipleStatesButton.OnStateChangedListener<Command.Direction>() { // from class: com.bq.zowi.components.timeline.SelectedCommandRowView.3
            @Override // com.bq.zowi.components.MultipleStatesButton.OnStateChangedListener
            public void onStateChanged(Command.Direction direction) {
                if (SelectedCommandRowView.this.listener != null) {
                    SelectedCommandRowView.this.listener.onDirectionChanged(direction);
                }
            }
        });
    }

    private void invalidateIcon() {
        this.iconImageView.setImageDrawable(this.iconDrawable);
    }

    private void invalidateAllowedRepetitions() {
        this.repetitionsTristateButton.setVisibility(this.isRepeatible ? 0 : 8);
    }

    private void invalidateAllowedDurations() {
        if (this.allowedDurations.length > 0) {
            HashMap<Long, Drawable> durations = new HashMap<>();
            durations.put(this.allowedDurations[2], ContextCompat.getDrawable(getContext(), R.drawable.speed_low_button_selector));
            durations.put(this.allowedDurations[1], ContextCompat.getDrawable(getContext(), R.drawable.speed_medium_button_selector));
            durations.put(this.allowedDurations[0], ContextCompat.getDrawable(getContext(), R.drawable.speed_fast_button_selector));
            this.durationTristateButton.setStates(durations, this.allowedDurations[1]);
            this.durationTristateButton.setVisibility(0);
            return;
        }
        this.durationTristateButton.setVisibility(8);
    }

    private void invalidateAllowedDirections() {
        if (this.allowedDirections.length > 0) {
            HashMap<Command.Direction, Drawable> directions = new HashMap<>();
            for (Command.Direction direction : this.allowedDirections) {
                directions.put(direction, resolveDrawableByDirection(direction));
            }
            this.directionTristateButton.setStates(directions, this.allowedDirections[0]);
            this.directionTristateButton.setVisibility(0);
            return;
        }
        this.directionTristateButton.setVisibility(8);
    }

    private Drawable resolveDrawableByDirection(Command.Direction direction) {
        switch (direction) {
            case FORWARD:
                return ContextCompat.getDrawable(getContext(), R.drawable.direction_front_button_selector);
            case BACKWARD:
                return ContextCompat.getDrawable(getContext(), R.drawable.direction_back_button_selector);
            case LEFT:
                return ContextCompat.getDrawable(getContext(), R.drawable.direction_left_button_selector);
            case RIGHT:
                return ContextCompat.getDrawable(getContext(), R.drawable.direction_right_button_selector);
            default:
                return null;
        }
    }
}

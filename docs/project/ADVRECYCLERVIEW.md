# ADVANCED RECYCLERVIEW DOCUMENTATION

## What is an Android RecyclerView?

An Android RecyclerView is a fundamental UI component introduced in Android 5.0 (Lollipop) as a more advanced and efficient replacement for the older ListView and GridView widgets. It serves as a versatile layout manager that efficiently displays large sets of data by recycling (reusing) view holders as they scroll off the screen.

### Core Characteristics:

- **View Recycling**: Automatically recycles view holders to conserve memory and maintain smooth scrolling performance with large datasets
- **Layout Flexibility**: Can be arranged in linear (vertical/horizontal), grid, or staggered layouts through different LayoutManagers
- **Performance Optimized**: Uses a ViewHolder pattern to minimize findViewById() calls and provide smooth 60fps scrolling
- **Item Animators**: Built-in support for item addition, removal, change, and move animations
- **Touch Interaction**: Supports complex touch-based operations like drag-and-drop, swipe, and item selection

### Primary Purposes:

1. **Efficient Data Display**: Handles large data sets (thousands of items) without performance issues
2. **Flexible Item Views**: Supports custom layouts and complex item designs
3. **Smooth Scrolling**: Provides fluid scrolling performance through view recycling
4. **User Interaction**: Enables rich touch interactions and item manipulation
5. **Dynamic Data Updates**: Seamlessly handles data changes with smooth animations

### Basic Usage Pattern:

```java
// Create RecyclerView
RecyclerView recyclerView = findViewById(R.id.recycler_view);

// Set layout manager
recyclerView.setLayoutManager(new LinearLayoutManager(this));

// Create adapter
MyAdapter adapter = new MyAdapter(dataList);
recyclerView.setAdapter(adapter);
```

## Overview

The `com.h6ah4i.android.widget.advrecyclerview` package is a comprehensive Android library that extends the standard Android RecyclerView widget with advanced interactive features and enhanced functionality. It provides a complete solution for creating sophisticated list interfaces in Android applications.

## Core Purpose

This library transforms the basic RecyclerView into a rich, interactive component supporting:
- **Drag and drop operations** for reordering items
- **Swipe gestures** for item actions (delete, archive, etc.)
- **Expandable items** for hierarchical data presentation
- **Custom animations** for smooth item transitions
- **Visual enhancements** through decorative elements

## Main Subpackages

### draggable

The `draggable` subpackage implements drag-and-drop functionality for RecyclerView items, enabling users to reorder list elements with smooth animations.

#### Key Components:

- **RecyclerViewDragDropManager** (`RecyclerViewDragDropManager.java`): Core manager class that handles the drag-and-drop logic, including item swapping and positioning.

- **DraggableItemAdapter** (`DraggableItemAdapter.java`): Interface for adapters that want to participate in drag-and-drop operations.

- **DraggableItemViewHolder** (`DraggableItemViewHolder.java`): Interface for view holders that support drag operations, providing visual feedback during drag.

- **DraggableItemConstants** (`DraggableItemConstants.java`): Collection of constants defining drag states and configuration options.

- **DraggableItemWrapperAdapter** (`DraggableItemWrapperAdapter.java`): Adapter wrapper that adds drag-and-drop capabilities to standard RecyclerView adapters.

- **DraggingItemInfo** (`DraggingItemInfo.java`): Data class containing information about the currently dragged item.

- **ItemDraggableRange** (`ItemDraggableRange.java`): Range implementation for defining which items can be dragged.

- **SwapTargetItemOperator** (`SwapTargetItemOperator.java`): Class responsible for calculating target positions during drag operations.

**Purpose**: Enables intuitive drag-and-drop list reordering with rubber band effect, visual feedback, and smooth animations.

### swipeable

The `swipeable` subpackage adds swipe gesture support to RecyclerView items, allowing users to swipe items to reveal actions or trigger item removal.

#### Key Components:

- **RecyclerViewSwipeManager** (`RecyclerViewSwipeManager.java`): Main manager that coordinates swipe operations and interactions with other features.

- **SwipeableItemAdapter** (`SwipeableItemAdapter.java`): Interface for adapters that want to support swipe actions.

- **BaseSwipeableItemAdapter** (`BaseSwipeableItemAdapter.java`): Base implementation of the swipeable adapter interface.

- **LegacySwipeableItemAdapter** (`LegacySwipeableItemAdapter.java`): Legacy adapter compatibility class.

- **SwipeableItemViewHolder** (`SwipeableItemViewHolder.java`): Interface for view holders that support swipe gestures.

- **AbstractSwipeableItemViewHolder** (`AbstractSwipeableItemViewHolder.java`): Abstract implementation of the swipeable item view holder interface.

- **SwipeResultAction** (`SwipeResultAction.java`): Abstract base class for actions that occur after a swipe gesture.

- **SwipeResultActionRemoveItem** (`SwipeResultActionRemoveItem.java`): Action to remove an item after a swipe.

- **SwipeResultActionMoveToSwipedDirection** (`SwipeResultActionMoveToSwipedDirection.java`): Action to move an item to its swiped direction.

- **SwipeResultActionDefault** (`SwipeResultActionDefault.java`): Default implementation for simple swipe actions.

- **SwipingItemOperator** (`SwipingItemOperator.java`): Class responsible for handling the visual aspects of item swiping.

**Purpose**: Provides flexible swipe gesture support with customizable reactions and smooth animations for item removal or action triggering.

### expandable

The `expandable` subpackage enables expandable/collapsible items within the RecyclerView, allowing for hierarchical data presentation and space-efficient lists.

#### Key Components:

- **ExpandableItemAdapter** (`ExpandableItemAdapter.java`): Main adapter interface that provides expand/collapse functionality for list items.

- **BaseExpandableSwipeableItemAdapter** (`BaseExpandableSwipeableItemAdapter.java`): Base implementation combining expandable and swipeable features.

- **ExpandableSwipeableItemAdapter** (`ExpandableSwipeableItemAdapter.java`): Adapter implementation that supports both expandable and swipeable items.

- **LegacyExpandableSwipeableItemAdapter** (`LegacyExpandableSwipeableItemAdapter.java`): Legacy adapter compatibility class.

- **LegacyExpandableSwipeResultAction** (`LegacyExpandableSwipeResultAction.java`): Legacy swipe result action for expandable items.

- **ExpandableItemViewHolder** (`ExpandableItemViewHolder.java`): Interface for view holders that support expand/collapse states.

- **AbstractExpandableItemViewHolder** (`AbstractExpandableItemViewHolder.java`): Abstract implementation of expandable item view holders.

- **RecyclerViewExpandableItemManager** (`RecyclerViewExpandableItemManager.java`): Manager class that handles expandable item states and animations.

- **ExpandableAdapterHelper** (`ExpandableAdapterHelper.java`): Helper class for common expandable adapter operations.

**Purpose**: Enables creation of expandable lists that can hide/show child items, ideal for folders, categories, or hierarchical data structures.

### animator

The `animator` subpackage provides sophisticated item animation capabilities, enhancing the visual appeal of RecyclerView interactions.

#### Key Components:

- **BaseItemAnimator** (`BaseItemAnimator.java`): Base implementation of item animator interface.

- **GeneralItemAnimator** (`GeneralItemAnimator.java`): General purpose item animator providing standard animations.

- **RefactoredDefaultItemAnimator** (`RefactoredDefaultItemAnimator.java`): Refactored version of the default item animator with improved performance.

- **SwipeDismissItemAnimator** (`SwipeDismissItemAnimator.java`): Special animator for swipe dismissal animations.

- **impl** (within animator): Implementation classes for specific animation types:
  - **ItemAddAnimationManager**: Manages add item animations
  - **ItemRemoveAnimationManager**: Manages remove item animations  
  - **ItemChangeAnimationManager**: Manages change item animations
  - **ItemMoveAnimationManager**: Manages move item animations

**Purpose**: Provides smooth, customizable animations for item operations (add, remove, change, move) to create polished user experiences.

### decoration

The `decoration` subpackage contains visual styling components that enhance the appearance of RecyclerView items.

#### Key Components:

- **SimpleListDividerDecorator** (`SimpleListDividerDecorator.java`): Adds simple dividers between list items.

- **ItemShadowDecorator** (`ItemShadowDecorator.java`): Adds drop shadows to list items for depth perception.

**Purpose**: Adds visual separation and depth to list items through decorative elements like dividers and shadows.

### utils

The `utils` subpackage contains utility classes and helper methods that support the main functionality of the library.

#### Key Components:

- **AbstractExpandableItemAdapter** (`AbstractExpandableItemAdapter.java`): Abstract base class for expandable item adapters.

- **AbstractDraggableItemViewHolder** (`AbstractDraggableItemViewHolder.java`): Abstract base class for draggable item view holders.

- **AbstractDraggableSwipeableItemViewHolder** (`AbstractDraggableSwipeableItemViewHolder.java`): Abstract base class for items supporting both drag and swipe.

- **AbstractSwipeableItemViewHolder** (`AbstractSwipeableItemViewHolder.java`): Abstract base class for swipeable item view holders.

- **BaseWrapperAdapter** (`BaseWrapperAdapter.java`): Base implementation of adapter wrapper class.

- **CustomRecyclerViewUtils** (`CustomRecyclerViewUtils.java`): Utility methods for RecyclerView customization.

- **RecyclerViewAdapterUtils** (`RecyclerViewAdapterUtils.java`): Common utility methods for RecyclerView adapters.

- **WrapperAdapterUtils** (`WrapperAdapterUtils.java`): Utility methods for wrapper adapters.

**Purpose**: Provides common functionality and helper methods to simplify implementation of advanced RecyclerView features.

### touchguard

The `touchguard` subpackage handles touch event interception and gesture management to prevent conflicts between different touch operations.

#### Key Components:

- **RecyclerViewTouchActionGuardManager** (`RecyclerViewTouchActionGuardManager.java`): Manages touch event distribution and prevents gesture conflicts.

**Purpose**: Ensures proper handling of touch events when multiple gesture handlers (drag, swipe, expand) are active simultaneously.

### event

The `event` subpackage provides event distribution and listener notification systems for RecyclerView interactions.

#### Key Components:

- **BaseRecyclerViewEventDistributor** (`BaseRecyclerViewEventDistributor.java`): Base class for event distribution logic.

- **RecyclerViewRecyclerEventDistributor** (`RecyclerViewRecyclerEventDistributor.java`): Event distributor for RecyclerView-specific events.

- **RecyclerViewEventDistributorListener** (`RecyclerViewEventDistributorListener.java`): Interface for listening to event distribution events.

**Purpose**: Manages event flow between different components and notifies listeners of important state changes.

## Integration Pattern

To use the Advanced RecyclerView library:

1. **Choose your features**: Select which subpackages you need (draggable, swipeable, expandable, etc.)
2. **Implement the interfaces**: Create view holders and adapters by implementing the relevant interfaces
3. **Use wrapper adapters**: Wrap your standard RecyclerView adapter with the appropriate wrapper (e.g., DraggableItemWrapperAdapter)
4. **Set up managers**: Initialize the necessary managers in your activity/fragment
5. **Connect components**: Link your views with the manager instances

## Benefits

- **Improved UX**: Provides intuitive touch-based interactions that users expect from modern applications
- **Flexibility**: Supports various combinations of features (drag + swipe + expand)
- **Performance**: Optimized for smooth animations and responsive interactions
- **Consistency**: Provides a consistent API across different Android versions
- **Extensibility**: Easy to add custom gestures and animations

## Dependencies

This library typically depends on the Android Support Library (RecyclerView and ViewPager) and requires Android API level 14+ for full functionality.

## Usage Examples

Typical usage patterns include:

1. **Simple drag-and-drop**: For reordering items in a list
2. **Swipe actions**: For deleting or archiving items
3. **Expandable lists**: For showing/hiding child items
4. **Combined features**: For complex list interactions with multiple gesture types

This documentation covers the core functionality of the Advanced RecyclerView library and provides a foundation for implementing advanced list interfaces in Android applications.
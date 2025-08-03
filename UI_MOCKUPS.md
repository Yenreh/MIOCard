# UI Mockups for Edit/Delete Functionality

## Original CardItem Layout (Before Changes)
```
┌─────────────────────────────────────────────────────────┐
│ Card Name                              [Refresh]         │
│ Card ID Display                                          │
│                                                          │
│ Balance: $XX.XX        Last update: XX/XX/XX             │
└─────────────────────────────────────────────────────────┘
```

## Updated CardItem Layout (After Changes) 
```
┌─────────────────────────────────────────────────────────┐
│ Card Name                    [Edit] [Delete] [Refresh]   │
│ Card ID Display                                          │
│                                                          │
│ Balance: $XX.XX        Last update: XX/XX/XX             │
└─────────────────────────────────────────────────────────┘
```

## Edit Card Screen Layout
```
┌─────────────────────────────────────────────────────────┐
│ [←] Edit Card                                            │
├─────────────────────────────────────────────────────────┤
│                                                          │
│ Card ID *                                                │
│ ┌─────────────────────────────────────────────────────┐ │
│ │ 1234567890 (read-only, grayed out)                 │ │
│ └─────────────────────────────────────────────────────┘ │
│                                                          │
│ Prefix                                                   │
│ ┌─────────────────────────────────────────────────────┐ │
│ │ Optional prefix                                     │ │
│ └─────────────────────────────────────────────────────┘ │
│                                                          │
│ Suffix                                                   │
│ ┌─────────────────────────────────────────────────────┐ │
│ │ Optional suffix                                     │ │
│ └─────────────────────────────────────────────────────┘ │
│                                                          │
│ Name *                                                   │
│ ┌─────────────────────────────────────────────────────┐ │
│ │ Card name                                           │ │
│ └─────────────────────────────────────────────────────┘ │
│                                                          │
│ ┌─────────────────────────────────────────────────────┐ │
│ │              Save Changes                           │ │
│ └─────────────────────────────────────────────────────┘ │
│                                                          │
└─────────────────────────────────────────────────────────┘
```

## Delete Confirmation Dialog
```
┌─────────────────────────────────────────────────────────┐
│                                                          │
│           Delete Card                                    │
│                                                          │
│   Are you sure you want to delete "My Card"?            │
│   This action cannot be undone.                         │
│                                                          │
│              [Cancel]    [Delete]                        │
│                                                          │
└─────────────────────────────────────────────────────────┘
```

## Icon Descriptions
- **Edit Icon**: Material Design edit icon (pencil)
- **Delete Icon**: Material Design delete icon (trash can) in error color
- **Size**: 18dp icons in 32dp clickable areas
- **Colors**: 
  - Edit: Primary theme color
  - Delete: Error theme color
  - Refresh: Default button colors

## Button Interactions
1. **Edit Button**: Opens EditCardScreen with pre-populated form
2. **Delete Button**: Shows confirmation dialog
3. **Refresh Button**: Unchanged behavior (fetches latest balance)

## Loading States
- **Edit Screen**: Shows loading spinner while fetching card data
- **Save Button**: Shows "Saving..." text with spinner during update
- **Delete Dialog**: Shows "Deleting..." text with disabled buttons during deletion

## Error States
- **Edit Screen**: Red error card below form if save fails
- **Delete Dialog**: Error message in MainScreen after dialog dismisses
- **Form Validation**: Red outline and error text for required name field
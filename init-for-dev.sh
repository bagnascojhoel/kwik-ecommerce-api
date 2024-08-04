#!/bin/bash

# Define the source and destination directories
source_dir="dev-tools/githooks"
destination_dir="../.git/modules/kwik-ecommerce-api/hooks"

# Check if the source directory exists
if [ ! -d "$source_dir" ]; then
  echo "Error: Source directory '$source_dir' not found."
  exit 1
fi

# Check if the destination directory exists, error if not exists
if [ ! -d "$destination_dir" ]; then
  echo "Error: Destination directory '$destination_dir' not found"
  exit 1
fi

# Copy all scripts from source to destination
echo "Copying scripts from '$source_dir' to '$destination_dir'"
cp -v "$source_dir"/* "$destination_dir"/

# Make all scripts executable
echo "Making scripts executable"
chmod +x "$destination_dir"/*

# Check if the copy operation was successful
if [ $? -eq 0 ]; then
  echo "Scripts successfully copied to '$destination_dir'"
else
  echo "Error: Failed to copy scripts to '$destination_dir'"
fi

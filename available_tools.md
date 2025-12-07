- `list_directory`
  Lists the names of files and subdirectories directly within a specified directory path. Can optionally ignore entries matching provided glob patterns.

- `read_file`
  Reads and returns the content of a specified file. If the file is large, the content will be truncated. The tool's response will clearly indicate if truncation has occurred and will provide details on how to read more of the file using the 'offset' and 'limit' parameters. Handles text, images (PNG, JPG, GIF, WEBP, SVG, BMP), and PDF files. For text files, it can read specific line ranges.

- `search_file_content`
  FAST, optimized search powered by `ripgrep`. PREFERRED over standard `run_shell_command("grep ...")` due to better performance and automatic output limiting (max 20k matches).

- `glob`
  Efficiently finds files matching specific glob patterns (e.g., `src/**/*.ts`, `**/*.md`), returning absolute paths sorted by modification time (newest first). Ideal for quickly locating files based on their name or path structure, especially in large codebases.

- `replace`
  Replaces text within a file. By default, replaces a single occurrence, but can replace multiple occurrences when `expected_replacements` is specified. This tool requires providing significant context around the change to ensure precise targeting. Always use the read_file tool to examine the file's current content before attempting a text replacement.

- `write_file`
  Writes content to a specified file in the local filesystem.

- `web_fetch`
  Processes content from URL(s), including local and private network addresses (e.g., localhost), embedded in a prompt. Include up to 20 URLs and instructions (e.g., summarize, extract specific data) directly in the 'prompt' parameter.

- `run_shell_command`
  This tool executes a given shell command as `bash -c <command>`. Command can start background processes using `&`. Command is executed as a subprocess that leads its own process group. Command process group can be terminated as `kill -- -PGID` or signaled as `kill -s SIGNAL -- -PGID`.

- `save_memory`
  Saves a specific piece of information or fact to your long-term memory.

- `google_web_search`
  Performs a web search using Google Search (via the Gemini API) and returns the results. This tool is useful for finding information on the internet based on a query.

- `write_todos`
  This tool can help you list out the current subtasks that are required to be completed for a given user request. The list of subtasks helps you keep track of the current task, organize complex queries and help ensure that you don't miss any steps.

- `codebase_investigator`
  The specialized tool for codebase analysis, architectural mapping, and understanding system-wide dependencies. Invoke this tool for tasks like vague requests, bug root-cause analysis, system refactoring, comprehensive feature implementation or to answer questions about the codebase that require investigation. It returns a structured report with key file paths, symbols, and actionable architectural insights.
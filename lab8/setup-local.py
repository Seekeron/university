"""Create local database credentials without publishing them."""
from pathlib import Path
import secrets

root = Path(__file__).resolve().parent
env_file = root / '.env'
if not env_file.exists():
    env_file.write_text('POSTGRES_PASSWORD=' + secrets.token_hex(24) + '\n')
    env_file.chmod(0o600)
config = root / 'application-local.properties'
if not config.exists():
    values = dict(line.split('=', 1) for line in env_file.read_text().splitlines()
                  if line and not line.startswith('#') and '=' in line)
    config.write_text('spring.datasource.url=jdbc:postgresql://localhost:55438/event_db\n'
                      'spring.datasource.username=event_app\n'
                      'spring.datasource.password=' + values['POSTGRES_PASSWORD'] + '\n')
    config.chmod(0o600)

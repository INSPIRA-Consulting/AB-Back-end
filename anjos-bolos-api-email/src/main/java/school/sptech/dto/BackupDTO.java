package school.sptech.dto;

public record BackupDTO(
        String nomeArquivo,
        String caminhoArquivo,
        String dataBackup,
        String status
) {}